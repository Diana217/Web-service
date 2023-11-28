package org.example;

import com.baeldung.soap.ws.client.generated.WebService;
import com.baeldung.soap.ws.client.generated.WebServiceSoap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.model.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebServiceClient {

    private static Scanner scanner = new Scanner(System.in);
    private static WebServiceSoap webServiceSoap;

    public static void main(String[] args) {
        try {
            WebService stub = new WebService();
            webServiceSoap = stub.getWebServiceSoap();
            chooseDBAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void chooseDBAction() throws JsonProcessingException {
        System.out.println("\nChoose the command: \n1. open DB \n2. create DB \n3. exit");
        String command = scanner.nextLine();
        int dbId = 0;
        switch (command) {
            case "1":
                dbId = openDatabase();
                chooseTablesAction(dbId);
                break;
            case "2":
                dbId = createDatabase();
                chooseTablesAction(dbId);
                break;
            case "3":
                System.exit(0);
                break;
        }
    }

    private static int openDatabase() {
        ObjectMapper mapper = new ObjectMapper();
        String result = webServiceSoap.getDBs();
        System.out.println("DBs: ");
        try {
            List<Database> databases = mapper.readValue(result, new TypeReference<List<Database>>() {});
            for (Database database : databases) {
                System.out.println("DB id: " + database.getId() + ", DB name: " + database.getDBName());
            }
            System.out.println("Enter DB id: ");
            int dbId = Integer.parseInt(scanner.nextLine());
            return dbId;
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static int createDatabase() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Enter DB name: ");
        String dbName = scanner.nextLine();
        String result = webServiceSoap.createDB(dbName);

        Map<String, Object> deserializedData = mapper.readValue(result, Map.class);
        Object id = deserializedData.get("Id");
        showMessage(result);
        return (int) id;
    }

    private static void chooseTablesAction(int dbId) throws JsonProcessingException {
        System.out.println("\nChoose the command: \n1. show tables \n2. create table \n3. back \n4. exit");
        String command = scanner.nextLine();
        switch (command) {
            case "1":
                showTables(dbId);
                chooseTableAction(dbId);
                break;
            case "2":
                createTable(dbId);
                chooseTableAction(dbId);
                break;
            case "3":
                chooseDBAction();
                break;
            case "4":
                System.exit(0);
                break;
        }
    }

    private static void showTables(int dbId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String result = webServiceSoap.getTables(dbId);
        List<Table> tables = mapper.readValue(result, new TypeReference<List<Table>>() {});
        System.out.println("DB tables: " + tables.stream().count());
        for (Table table : tables) {
            System.out.println("Table id: " + table.getId() + "\nTable name: " + table.getTableName() + "\n");
        }
    }

    private static void createTable(int dbId) throws JsonProcessingException {
        System.out.println("Enter table name: ");
        String tableName = scanner.nextLine();
        String result = webServiceSoap.addTable(tableName, dbId);
        showMessage(result);
        showTables(dbId);
    }

    private static void chooseTableAction(int dbId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("\nChoose the command: \n1. edit table \n2. delete table \n3. union of tables \n4. back \n5. exit");
        String command = scanner.nextLine();
        int tableId;
        switch (command) {
            case "1":
                System.out.println("Enter table id: ");
                tableId = Integer.parseInt(scanner.nextLine());
                String result = webServiceSoap.getTable(tableId);

                Table table = mapper.readValue(result, new TypeReference<Table>() {});
                showTable(tableId);
                editTable(table);
                break;
            case "2":
                System.out.println("Enter table id: ");
                tableId = Integer.parseInt(scanner.nextLine());
                deleteTable(tableId);
                showTables(dbId);
                chooseTableAction(dbId);
                break;
            case "3":
                showTables(dbId);
                System.out.println("Enter table1 id: ");
                int table1Id = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter table2 id: ");
                int table2Id = Integer.parseInt(scanner.nextLine());
                String res = webServiceSoap.getTablesField(table1Id, table2Id);

                List<String> fieldsNames = mapper.readValue(res, new TypeReference<List<String>>() {});
                System.out.println("Fields: ");
                for (String field : fieldsNames) {
                    System.out.println(field);
                }
                System.out.println("Enter field name: ");
                String fieldName = scanner.nextLine();

                String r = webServiceSoap.unionOfTables(table1Id, table2Id, fieldName);
                Table t = mapper.readValue(r, new TypeReference<Table>() {});
                showTable(t.getId());
                chooseTableAction(dbId);
                break;
            case "4":
                chooseTablesAction(dbId);
                break;
            case "5":
                System.exit(0);
                break;
        }
    }

    private static void editTable(Table table) throws JsonProcessingException {
        boolean hasColumns = table.getColumns().stream().count() > 0;
        boolean hasRows = table.getRows().stream().count() > 0;
        if (hasColumns && hasRows){
            System.out.println("\nChoose the command: \n1. add column \n2. add row \n3. modify row \n4. back \n5. exit");
        }
        else if (hasColumns && !hasRows){
            System.out.println("\nChoose the command: \n1. add column \n2. add row \n4. back \n5. exit\n");
        }
        else {
            System.out.println("\nChoose the command: \n1. add column \n4. back \n5. exit\n");
        }
        String command = scanner.nextLine();
        switch (command) {
            case "1":
                Table updTable = addColumn(table.getId());
                editTable(updTable);
                break;
            case "2":
                updTable = addRow(table.getId());
                editTable(updTable);
                break;
            case "3":
                //show
                updTable = modifyRow(table.getId());
                editTable(updTable);
                break;
            case "4":
                chooseTableAction(table.getDatabaseId());
                break;
            case "5":
                System.exit(0);
                break;
        }
    }

    private static void deleteTable(int tableId) throws JsonProcessingException {
        String result = webServiceSoap.deleteTable(tableId);
        showMessage(result);
    }

    private static Table addColumn(int tableId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Types:\n");
        String result = webServiceSoap.getTypes();

        List<Type> types = mapper.readValue(result, new TypeReference<List<Type>>() {});
        for (Type type : types) {
            System.out.println("Type id: " + type.getId() + "\nType name: " + type.getName() + "\n");
        }
        System.out.println("Enter type id: ");
        int typeId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter column name: ");
        String columnName = scanner.nextLine();

        String res = webServiceSoap.addColumn(tableId, columnName, typeId);
        showMessage(res);

        String t = webServiceSoap.getTable(tableId);
        Table table = mapper.readValue(t, new TypeReference<Table>() {});
        showTable(table.getId());
        return table;
    }

    private static Table addRow(int tableId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String result = webServiceSoap.addRow(tableId);
        showMessage(result);

        String t = webServiceSoap.getTable(tableId);
        Table table = mapper.readValue(t, new TypeReference<Table>() {});
        showTable(table.getId());
        return table;
    }

    private static Table modifyRow(int tableId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Enter column id: ");
        int columnId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter row index: ");
        int rowIndex = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter new value: ");
        String newValue = scanner.nextLine();

        String result = webServiceSoap.changeValue(newValue, tableId, columnId, rowIndex);
        showMessage(result);
        String t = webServiceSoap.getTable(tableId);
        Table table = mapper.readValue(t, new TypeReference<Table>() {});
        showTable(table.getId());
        return table;
    }

    private static void showMessage(String result) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> deserializedData = mapper.readValue(result, Map.class);
        String res = (String) deserializedData.get("Message");
        System.out.println(res);
    }

    private static void showTable(int tableId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String t = webServiceSoap.getTable(tableId);
        Table table = mapper.readValue(t, new TypeReference<Table>() {});

        System.out.println("Table id: " + table.getId() + "\nTable name: " + table.getTableName() + "\n");
        for (Column column : table.getColumns()) {
            System.out.print(padRight(String.format("%s (%s)", column.getColName(), column.getColType().getName())));
        }
        System.out.println();
        for (Column column : table.getColumns()) {
            System.out.print(padRight(String.format("id: %d", column.getId())));
        }
        System.out.println();

        for (Row row : table.getRows()) {
            System.out.print(padRight(String.format("index: %d", table.getRows().indexOf(row))));
            System.out.println();
            List<String> values = mapper.readValue(row.getRowValues(), new TypeReference<List<String>>() {});
            for (String value : values) {
                System.out.print(padRight(String.format("%s", value)));
            }
            System.out.println();
        }
    }

    public static String padRight(String input) {
        return String.format("%-" + 20 + "s", input);
    }
}