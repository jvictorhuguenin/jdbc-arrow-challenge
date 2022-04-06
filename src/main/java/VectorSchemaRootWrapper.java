import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.Float8Vector;
import org.apache.arrow.vector.VarCharVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.types.pojo.Schema;
import org.apache.arrow.vector.util.Text;

public class VectorSchemaRootWrapper implements AutoCloseable {
  private final RootAllocator rootAllocator = new RootAllocator(Long.MAX_VALUE);

  private final VectorSchemaRoot vectorSchemaRoot;

  public VectorSchemaRootWrapper(Schema schema) {
    vectorSchemaRoot = VectorSchemaRoot.create(schema, rootAllocator);
  }

  public void addDouble(double item, String fieldName, int count) {
    ((Float8Vector) vectorSchemaRoot.getVector(fieldName)).setSafe(count, item);
    vectorSchemaRoot.setRowCount(count);
  }

  public void addVarchar(String item, String fieldName, int count) {
    ((VarCharVector) vectorSchemaRoot.getVector(fieldName)).setSafe(count, new Text(item));
    vectorSchemaRoot.setRowCount(count);
  }

  public VectorSchemaRoot getVectorSchemaRoot() {
    return vectorSchemaRoot;
  }

  @Override
  public void close() {
    rootAllocator.close();
  }
}
