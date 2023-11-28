using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApplication.Classes
{
    public class Table
    {
        public int Id { get; set; }
        public string TableName { get; set; }
        public int DatabaseId { get; set; }
        public List<Column> Columns { get; set; }
        public List<Row> Rows { get; set; }

        public Table() { }
        public Table(string name)
        {
            TableName = name;
            Columns = new List<Column>();
            Rows = new List<Row>();
        }
    }
}