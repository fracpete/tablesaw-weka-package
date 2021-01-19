/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *    Tablesaw.java
 *    Copyright (C) 2021 University of Waikato, Hamilton, New Zealand
 *
 */

package weka.filters.unsupervised.attribute;

import tech.tablesaw.api.Table;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.Instances;
import weka.core.Option;
import weka.core.RevisionUtils;
import weka.core.TablesawUtils;
import weka.core.Utils;
import weka.core.tablesaw.ColumnOperation;
import weka.core.tablesaw.PassThrough;
import weka.filters.SimpleBatchFilter;
import weka.filters.UnsupervisedFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 <!-- globalinfo-start -->
 * Applies the selected column operation to the data.
 * <br><br>
 <!-- globalinfo-end -->
 *
 <!-- options-start -->
 * Valid options are: <p>
 *
 * <pre> -operation &lt;classname + options&gt;
 *  The column operation to apply.
 *  (default: weka.core.tablesaw.PassThrough)</pre>
 *
 * <pre> -output-debug-info
 *  If set, filter is run in debug mode and
 *  may output additional info to the console</pre>
 *
 * <pre> -do-not-check-capabilities
 *  If set, filter capabilities are not checked before filter is built
 *  (use with caution).</pre>
 *
 <!-- options-end -->
 * 
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class Tablesaw
  extends SimpleBatchFilter
  implements UnsupervisedFilter {

  /** for serialization */
  private final static long serialVersionUID = 8349568310991609867L;

  /** the column operation to use. */
  protected ColumnOperation m_Operation = new PassThrough();

  /** the processed data. */
  protected transient Instances m_Processed;

  /**
   * Returns a string describing this filter
   *
   * @return a description of the filter suitable for
   * displaying in the explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Applies the selected column operation to the data.";
  }

  /**
   * Resets the filter.
   */
  @Override
  protected void reset() {
    super.reset();

    m_Processed = null;
  }

  /**
   * Returns an enumeration describing the available options.
   * 
   * @return an enumeration of all the available options.
   */
  @Override
  public Enumeration<Option> listOptions() {
    Vector<Option> result = new Vector<Option>();

    result.addElement(new Option(
      "\tThe column operation to apply.\n"
        + "\t(default: " + PassThrough.class.getName() + ")",
      "operation", 1, "-operation <classname + options>"));

    result.addAll(Collections.list(super.listOptions()));

    return result.elements();
  }

  /**
   * Gets the current settings of the filter.
   * 
   * @return an array of strings suitable for passing to setOptions
   */
  @Override
  public String[] getOptions() {
    List<String> result = new ArrayList<String>();
    
    result.add("-operation");
    result.add(Utils.toCommandLine(m_Operation));

    Collections.addAll(result, super.getOptions());

    return result.toArray(new String[result.size()]);
  }

  /**
   * Parses a given list of options.
   *
   * @param options the list of options as an array of strings
   * @throws Exception if an option is not supported
   */
  @Override
  public void setOptions(String[] options) throws Exception {
    String 	tmpStr;
    String	classname;
    String[]	tmpOptions;

    tmpStr = Utils.getOption("operation", options);
    if (tmpStr.isEmpty()) {
      setOperation(new PassThrough());
    }
    else {
      tmpOptions    = Utils.splitOptions(tmpStr);
      classname     = tmpOptions[0];
      tmpOptions[0] = "";
      setOperation((ColumnOperation) Utils.forName(Object.class, classname, tmpOptions));
    }

    super.setOptions(options);

    Utils.checkForRemainingOptions(options);
  }

  /**
   * Sets the column operation to use.
   *
   * @param value the operation
   */
  public void setOperation(ColumnOperation value) {
    m_Operation = value;
  }

  /**
   * Gets the column operation in use.
   *
   * @return the operation
   */
  public ColumnOperation getOperation() {
    return m_Operation;
  }

  /**
   * Returns the tip text for this property
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String operationTipText() {
    return "The column operation to apply.";
  }

  /**
   * Returns the Capabilities of this filter.
   * 
   * @return the capabilities of this object
   * @see Capabilities
   */
  @Override
  public Capabilities getCapabilities() {
    Capabilities	result;

    result = new Capabilities(this);
    result.enableAllClasses();
    result.enable(Capability.NO_CLASS);
    result.enableAllAttributes();
    result.setMinimumNumberInstances(0);

    return result;
  }

  /**
   * Returns whether to allow the determineOutputFormat(Instances) method access
   * to the full dataset rather than just the header.
   *
   * @return whether determineOutputFormat has access to the full input dataset
   */
  public boolean allowAccessToFullInputFormat() {
    return true;
  }

  /**
   * Converts the data.
   *
   * @param input	the data to convert
   * @return		the converted data
   * @throws Exception	if conversion fails for some reason
   */
  protected Instances convert(Instances input) throws Exception {
    Table	tableIn;
    Table	tableOut;

    tableIn     = TablesawUtils.instancesToTable(input);
    tableOut    = m_Operation.processColumns(tableIn);

    return TablesawUtils.tableToInstances(tableOut);
  }

  /**
   * Determines the output format based on the input format and returns this. In
   * case the output format cannot be returned immediately, i.e.,
   * hasImmediateOutputFormat() returns false, then this method will called from
   * batchFinished() after the call of preprocess(Instances), in which, e.g.,
   * statistics for the actual processing step can be gathered.
   * 
   * @param inputFormat the input format to base the output format on
   * @return the output format
   * @throws Exception in case the determination goes wrong
   */
  @Override
  protected Instances determineOutputFormat(Instances inputFormat) throws Exception {
    m_Processed = convert(inputFormat);
    return new Instances(m_Processed, 0);
  }

  /**
   * Processes the given data (may change the provided dataset) and returns the
   * modified version. This method is called in batchFinished().
   *
   * @param instances the data to process
   * @return the modified data
   * @throws Exception in case the processing goes wrong
   * @see #batchFinished()
   */
  @Override
  protected Instances process(Instances instances) throws Exception {
    Instances	result;

    if (m_Processed != null) {
      result = m_Processed;
      m_Processed = null;
    }
    else {
      result = convert(instances);
    }

    return result;
  }

  /**
   * Returns the revision string.
   * 
   * @return		the revision
   */
  @Override
  public String getRevision() {
    return RevisionUtils.extract("$Revision: 1 $");
  }

  /**
   * Main method for executing this filter.
   *
   * @param args should contain arguments to the filter: 
   * use -h for help
   */
  public static void main(String[] args) {
    runFilter(new Tablesaw(), args);
  }
}
