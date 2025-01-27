import java.sql.SQLException;

import org.apache.arrow.vector.FieldVector;
import org.apache.arrow.vector.VectorSchemaRoot;

public class Main {
  public static void main(String[] args) throws Exception {
    String userName = args[0];
    String password = args[1];
    String urlPort = args[2];

    PostgresDriverWrapper driver = new PostgresDriverWrapper(urlPort, userName, password);
    driver.connect();
    VectorSchemaRoot vectorSchemaRoot = driver.executeQuery("select * from personaldata");
    driver.close();

    for (FieldVector fieldVector : vectorSchemaRoot.getFieldVectors()) {
      for (int i = 0; i < vectorSchemaRoot.getRowCount(); i++) {
        System.out.println(fieldVector.getObject(i));
      }
    }
    vectorSchemaRoot.close();
  }
}
