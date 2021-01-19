# tablesaw-weka-package
Weka package that takes advantage of the tablesaw Java dataframe and visualization library.

## Tablesaw dependencies

Use [bootstrapp](https://github.com/fracpete/bootstrapp) to download all
the dependencies (adjust version of tablesaw if necessary):

```commandline
java -jar bootstrapp-0.1.5-spring-boot.jar --dependency tech.tablesaw:tablesaw-core:0.38.1 --output_dir ./out
```

Copy all jars from `./out/target/lib` into the projects `lib` directory.
