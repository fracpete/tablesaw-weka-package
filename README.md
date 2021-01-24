# tablesaw-weka-package
Weka package that takes advantage of the [tablesaw](https://jtablesaw.github.io/tablesaw/) 
Java dataframe and visualization library.


## Tablesaw dependencies

Use [bootstrapp](https://github.com/fracpete/bootstrapp) to download all
the dependencies (adjust version of tablesaw if necessary):

```commandline
java -jar bootstrapp-0.1.5-spring-boot.jar --dependency tech.tablesaw:tablesaw-core:0.38.1 --output_dir ./out
```

Copy all jars from `./out/target/lib` into the projects `lib` directory.


## Plugins

### Input/Output

* Loader

  ```
  TODO
  ``` 
  
* Saver

  ```
  TODO
  ```
  
### Filter

The `weka.filters.Tablesaw` filter allows you to apply the following table
operation:

  * `PassThrough` - dummy, just passes through the data
  * `SampleN` - generates a sub-sample of size N
  * `SampleX` - generates a sub-sample of proportion X (0.0-1.0)
  * `Sort` - sorts the data using specified columns (ascending or descending)
  * `MultiTableOperation` - applies all specified table operations sequentially
  * ...

**Note:** Since the data needs to be converted into Tablesaw's dataframe format,
the data may still get modified (e.g., change in attribute types), despite the 
`PassThrough` operation being selected.


## Releases

* [????.??.??](https://github.com/fracpete/matlab-weka-package/releases/download/v????.??.??/tablesaw-????.??.??.zip)


## Maven

Use the following dependency in your `pom.xml`:

```xml
    <dependency>
      <groupId>com.github.fracpete</groupId>
      <artifactId>tablesaw-weka-package</artifactId>
      <version>????.??.??</version>
      <type>jar</type>
      <exclusions>
        <exclusion>
          <groupId>nz.ac.waikato.cms.weka</groupId>
          <artifactId>weka-dev</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
```


## How to use packages

For more information on how to install the package, see:

https://waikato.github.io/weka-wiki/packages/manager/
