using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Configuration;
using System.Web.Services;
using System.Configuration;
using System.Threading.Tasks;
using WebApplication.Classes;
using Newtonsoft.Json;
using Microsoft.EntityFrameworkCore;
using System.IO;

namespace WebApplication
{
    /// <summary>
    /// Summary description for WebService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.None)]
    //[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    [System.Web.Script.Services.ScriptService]
    public class WebService : System.Web.Services.WebService
    {
        private ApplicationContext db = new ApplicationContext();

        [WebMethod]
        public string CreateDB(string name)
        {
            int id;
            string message;
            if (name.Equals("") || db.Databases.Any(x => x.DBName == name))
            {
                id = 0;
                message = "Database with this name already exists!";
            }
            else
            {
                Database database = new Database(name);
                database.DBPath = "";
                db.Databases.Add(database);
                db.SaveChanges();
                id = database.Id;
                message = "Database created successfully!";
            }
            return JsonConvert.SerializeObject(new { Id = id, Message = message });
        }

        [WebMethod]
        public string GetDBs()
        {
            var dbs = db.Databases.ToList();
            return JsonConvert.SerializeObject(dbs);
        }

        [WebMethod]
        public string AddTable(string name,int dbId)
        {
            bool result;
            if (name.Equals(""))
                result = false;
            else if (db.Tables.Where(x => x.DatabaseId == dbId && x.TableName == name).Count() > 0)
                result = false;
            else
            {
                Table table = new Table();
                table.TableName = name;
                table.DatabaseId = dbId;
                db.Tables.Add(table);
                db.SaveChanges();
                result = true;
            }
            if(result)
                return JsonConvert.SerializeObject(new { Message = "Table created successfully!" });
            else
                return JsonConvert.SerializeObject(new { Message = "Failure!" });
        }

        [WebMethod]
        public string GetTables(int dbId)
        {
            var tables = db.Tables.Where(x => x.DatabaseId == dbId).ToList();
            return JsonConvert.SerializeObject(tables);
        }

        [WebMethod]
        public string GetTable(int tableId)
        {
            var table = db.Tables.Where(x => x.Id == tableId).Include(x => x.Columns).ThenInclude(x => x.ColType).Include(x => x.Rows).FirstOrDefault();
            return JsonConvert.SerializeObject(table);
        }

        [WebMethod]
        public string GetTypes()
        {
            var types = db.Types.ToList();
            return JsonConvert.SerializeObject(types);
        }

        [WebMethod]
        public string AddColumn(int tableId, string name, int typeId)
        {
            bool result;
            var table = db.Tables.Where(x => x.Id == tableId).Include(x => x.Columns).Include(x => x.Rows).FirstOrDefault();
            if (table == null)
                result = false;
            else if (db.Columns.Where(x => x.TableId == tableId && x.ColName == name).Count() > 0)
                result = false;
            else
            {
                Column column = new Column();
                column.ColName = name;
                column.TableId = tableId;
                column.TypeId = typeId;
                db.Columns.Add(column);
                db.SaveChanges();

                if (table.Rows != null && table.Rows.Count > 0)
                {
                    foreach (var row in table.Rows)
                    {
                        var values = JsonConvert.DeserializeObject<List<string>>(row.RowValues);
                        values.Add("");
                        row.RowValues = JsonConvert.SerializeObject(values);
                        db.Entry(row).State = EntityState.Modified;
                    }
                    db.SaveChanges();
                }
                result = true;
            }
            if(result)
                return JsonConvert.SerializeObject(new { Message = "Column created successfully!" });
            else
                return JsonConvert.SerializeObject(new { Message = "Failure!" });
        }

        [WebMethod]
        public string AddRow(int tableId)
        {
            bool result;
            var table = db.Tables.Where(x => x.Id == tableId).Include(x => x.Columns).Include(x => x.Rows).FirstOrDefault();
            if (table == null)
                result = false;
            else
            {
                Row row = new Row();
                row.TableId = tableId;
                var colsCount = table.Columns.Count;
                row.RowValues = JsonConvert.SerializeObject(new List<string>(colsCount));
                db.Rows.Add(row);
                db.SaveChanges();
                result = true;
            }
            if(result)
                return JsonConvert.SerializeObject(new { Message = "Row created successfully!" });
            else
                return JsonConvert.SerializeObject(new { Message = "Failure!" });
        }

        [WebMethod]
        public string ChangeValue(string newValue, int tableId, int columnId, int rIndex)
        {
            bool result;
            var table = db.Tables.Where(x => x.Id == tableId).Include(x => x.Columns).Include(x => x.Rows).FirstOrDefault();
            if (table == null)
                result = false;
            else
            {
                var column = db.Columns.Where(x => x.Id == columnId && x.TableId ==tableId).Include(x => x.ColType).FirstOrDefault();
                if (column == null || !column.ColType.Validate(newValue))
                    result = false;
                else
                {
                    var rows = db.Rows.Where(x => x.TableId == tableId).OrderBy(x => x.Id).ToList();
                    var rowToChange = rows[rIndex];
                    if (rowToChange == null)
                        result = false;
                    else
                    {
                        var values = JsonConvert.DeserializeObject<List<string>>(rowToChange.RowValues);
                        var columns = db.Columns.Where(x => x.TableId == tableId).ToList();
                        var colIndex = columns.IndexOf(column);
                        if (values.Count == 0 || colIndex >= values.Count)
                        {
                            while (values.Count <= colIndex)
                                values.Add("");
                        }
                        values[colIndex] = newValue;
                        rowToChange.RowValues = JsonConvert.SerializeObject(values);
                        db.Entry(rowToChange).State = EntityState.Modified;
                        db.SaveChanges();
                        result = true;
                    }
                }
            }
            if(result)
                return JsonConvert.SerializeObject(new { Message = "Value changed successfully!" });
            else
                return JsonConvert.SerializeObject(new { Message = "Failure!" });
        }

        [WebMethod]
        public string DeleteTable(int tableId)
        {
            bool result;
            var table = db.Tables.Where(x => x.Id == tableId).Include(x => x.Columns).Include(x => x.Rows).FirstOrDefault();
            if (table == null)
                result = false;
            else
            {
                var rowsToRemove = db.Rows.Where(x => x.TableId == tableId);
                foreach (var rowToRemove in rowsToRemove)
                    db.Rows.Remove(rowToRemove);
                var colsToRemove = db.Columns.Where(x => x.TableId == tableId);
                foreach (var colToRemove in colsToRemove)
                    db.Columns.Remove(colToRemove);
                db.Tables.Remove(table);
                result = true;
                db.SaveChanges();
            }
            if(result)
                return JsonConvert.SerializeObject(new { Message = "Table deleted successfully!" });
            else
                return JsonConvert.SerializeObject(new { Message = "Failure!" });
        }

        char sep = '$';
        char space = '#';
        [WebMethod]
        public string SaveDB(int id)
        {
            bool result;
            var database = db.Databases.Include(x => x.Tables).ThenInclude(x => x.Columns)
                .Include(x => x.Tables).ThenInclude(t => t.Rows).FirstOrDefault(x => x.Id == id);
            if (database == null)
                result = false;
            else
            {
                var path = @"C:\\Users\\dulko\\source\\repos\\WebServer\\databases\\" + database.DBName + "_" + DateTime.Now.ToString("yyyy-MM-dd") + ".tdb";
                database.DBPath = path;
                db.SaveChanges();

                StreamWriter sw = new StreamWriter(path);
                sw.WriteLine(database.DBName);
                foreach (Table t in database.Tables)
                {
                    sw.WriteLine(sep);
                    sw.WriteLine(t.TableName);
                    foreach (Column c in t.Columns)
                    {
                        sw.Write(c.ColName + space);
                    }
                    sw.WriteLine();
                    foreach (Column c in t.Columns)
                    {
                        var type = db.Types.Find(c.TypeId);
                        sw.Write(type.Name + space);
                    }
                    sw.WriteLine();
                    foreach (Row r in t.Rows)
                    {
                        var values = JsonConvert.DeserializeObject<List<string>>(r.RowValues);
                        foreach (string s in values)
                        {
                            sw.Write(s + space);
                        }
                        sw.WriteLine();
                    }
                }
                sw.Close();
                result = true;
            }
            if (result) 
                return JsonConvert.SerializeObject(new { Message = "Database saved successfully!" });
            else
                return JsonConvert.SerializeObject(new { Message = "Failure!" });
        }

        [WebMethod]
        public string OpenDB(string path)
        {
            StreamReader sr = new StreamReader(path);
            var DB = db.Databases.Where(x => x.DBPath == path).FirstOrDefault();

            string file = sr.ReadToEnd();
            string[] parts = file.Split(sep);

            Database database = new Database(parts[0]);
            for (int i = 1; i < parts.Length; ++i)
            {
                parts[i] = parts[i].Replace("\r\n", "\r");
                List<string> buf = parts[i].Split('\r').ToList();
                buf.RemoveAt(0);
                buf.RemoveAt(buf.Count - 1);

                if (buf.Count > 0)
                {
                    database.Tables.Add(new Table(buf[0]));
                }
                if (buf.Count > 2)
                {
                    string[] cname = buf[1].Split(space);
                    string[] ctype = buf[2].Split(space);
                    int length = cname.Length - 1;
                    for (int j = 0; j < length; ++j)
                    {
                        database.Tables[i - 1].Columns.Add(new Column(cname[j], ctype[j]));
                    }

                    for (int j = 3; j < buf.Count; ++j)
                    {
                        database.Tables[i - 1].Rows.Add(new Row());
                        List<string> values = buf[j].Split(space).ToList();
                        values.RemoveAt(values.Count - 1);
                        if (database.Tables[i - 1].Rows.Last().RowValues != null)
                        {
                            var rowValues = JsonConvert.DeserializeObject<List<string>>(database.Tables[i - 1].Rows.Last().RowValues);
                            rowValues.AddRange(values);
                            database.Tables[i - 1].Rows.Last().RowValues = JsonConvert.SerializeObject(rowValues);
                        }
                    }
                }
            }
            sr.Close();
            return JsonConvert.SerializeObject(database);
        }

        [WebMethod]
        public string GetTablesField(int id1, int id2)
        {
            var table1 = db.Tables.Where(x => x.Id == id1).Include(x => x.Columns).FirstOrDefault();
            var table2 = db.Tables.Where(x => x.Id == id2).Include(x => x.Columns).FirstOrDefault();
            List<string> colNames = new List<string>();
            if (table1 != null && table2 != null)
            {
                if (table1.Columns.Count > 0 && table2.Columns.Count > 0)
                    colNames = table1.Columns.Select(x => x.ColName).Intersect(table2.Columns.Select(x => x.ColName)).ToList();
            }
            return JsonConvert.SerializeObject(colNames);
        }

        [WebMethod]
        public string UnionOfTables(int id1, int id2, string field)
        {
            var table1 = db.Tables.Where(x => x.Id == id1).Include(x => x.Columns).Include(x => x.Rows).FirstOrDefault();
            var table2 = db.Tables.Where(x => x.Id == id2).Include(x => x.Columns).Include(x => x.Rows).FirstOrDefault();
            Table table = new Table("Union_" + field);
            table.DatabaseId = table1.DatabaseId;
            db.Tables.Add(table);
            db.SaveChanges();

            if (table1 != null && table2 != null)
            {
                if (table1.Columns.Select(x => x.ColName).Contains(field) && table2.Columns.Select(x => x.ColName).Contains(field))
                {
                    foreach (var col in table1.Columns.Union(table2.Columns).ToList())
                    {
                        Column column = new Column();
                        column.ColName = col.ColName;
                        column.TableId = table.Id;
                        column.TypeId = col.TypeId;
                        column.ColType = col.ColType;
                        db.Columns.Add(column);
                    }
                    db.SaveChanges();

                    var col1 = table1.Columns.Where(x => x.ColName == field).FirstOrDefault();
                    var col2 = table2.Columns.Where(x => x.ColName == field).FirstOrDefault();
                    if (col1 != null && col2 != null)
                    {
                        var ind1 = table1.Columns.IndexOf(col1);
                        var ind2 = table2.Columns.IndexOf(col2);
                        bool isEnum = false;
                        var col1Type = db.Types.Find(col1.TypeId);
                        var col2Type = db.Types.Find(col2.TypeId);
                        if (col1Type is TypeEnum && col2Type is TypeEnum)
                            isEnum = true;
                        if (ind1 != -1 && ind2 != -1)
                        {
                            foreach (var row1 in table1.Rows)
                            {
                                var list1 = JsonConvert.DeserializeObject<List<string>>(row1.RowValues);
                                var value1 = list1[ind1];
                                foreach (var row2 in table2.Rows)
                                {
                                    var list2 = JsonConvert.DeserializeObject<List<string>>(row2.RowValues);
                                    var value2 = list2[ind2];
                                    if (isEnum)
                                    {
                                        var enum1 = value1.Split(',');
                                        var enum2 = value2.Split(',');
                                        if (enum1.All(x => enum2.Contains(x)) && enum2.All(x => enum1.Contains(x)))
                                        {
                                            var newRow = new Row();
                                            List<string> list = new List<string>();
                                            list.AddRange(list1);
                                            list.AddRange(list2);
                                            newRow.TableId = table.Id;
                                            newRow.RowValues = JsonConvert.SerializeObject(list);
                                            db.Rows.Add(newRow);
                                        }
                                    }
                                    else if (value1 == value2)
                                    {
                                        var newRow = new Row();
                                        List<string> list = new List<string>();
                                        list.AddRange(list1);
                                        list.AddRange(list2);
                                        newRow.TableId = table.Id;
                                        newRow.RowValues = JsonConvert.SerializeObject(list);
                                        db.Rows.Add(newRow);
                                    }
                                }
                            }
                            db.SaveChanges();
                        }
                    }
                }
            }
            table.Columns = db.Columns.Where(x => x.TableId == table.Id).ToList();
            table.Rows = db.Rows.Where(x => x.TableId == table.Id).ToList();
            var colToRemove = db.Columns.Where(x => x.TableId == table.Id && x.ColName == field).FirstOrDefault();
            var ind = table.Columns.IndexOf(colToRemove);
            if (ind != -1)
            {
                db.Columns.Remove(colToRemove);
                foreach (var row in table.Rows)
                {
                    var rowlist = JsonConvert.DeserializeObject<List<string>>(row.RowValues);
                    if (rowlist.Count > ind)
                    {
                        rowlist.RemoveAt(ind);
                        row.RowValues = JsonConvert.SerializeObject(rowlist);
                    }
                }
                db.SaveChanges();
            }
            return JsonConvert.SerializeObject(table);
        }
    }
}
