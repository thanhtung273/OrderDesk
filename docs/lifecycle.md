\# Maven Lifecycle Observations



\- `mvn clean`: Deletes the entire `target/` directory and removes all previously compiled artifacts.

\- `mvn compile`: Creates `target/classes/` containing the compiled `.class` bytecode files from `src/main/java`.

\- `mvn test`: Compiles test sources into `target/test-classes/`, executes tests, and generates execution reports inside `target/surefire-reports/`.

\- `mvn package`: Packages the compiled application classes into a distributable archive file `target/orderdesk-1.0-SNAPSHOT.jar`.



\## Deliberate Compilation Error



```text

\[ERROR] /src/main/java/com/fsa/orderdesk/App.java:\[15,28] cannot find symbol

\[ERROR]   symbol:   variable undefinedVariable

\[ERROR]   location: class com.fsa.orderdesk.App

```

