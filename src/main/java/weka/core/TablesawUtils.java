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
 * TablesawUtils.java
 * Copyright (C) 2021 University of Waikato, Hamilton, NZ
 */

package weka.core;

import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TextColumn;
import tech.tablesaw.columns.Column;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper class for data operations.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class TablesawUtils {

  /**
   * Checks whether the column is numeric.
   *
   * @param type	the column type
   * @return		true if numeric
   */
  public static boolean isNumeric(ColumnType type) {
    if (type == ColumnType.DOUBLE)
      return true;
    if (type == ColumnType.FLOAT)
      return true;
    if (type == ColumnType.SHORT)
      return true;
    if (type == ColumnType.INTEGER)
      return true;
    if (type == ColumnType.LONG)
      return true;
    return false;
  }

  /**
   * Checks whether the column is date-like.
   *
   * @param type	the column type
   * @return		true if date-like
   */
  public static boolean isDateLike(ColumnType type) {
    if (type == ColumnType.LOCAL_TIME)
      return true;
    if (type == ColumnType.LOCAL_DATE)
      return true;
    if (type == ColumnType.LOCAL_DATE_TIME)
      return true;
    return false;
  }

  /**
   * Turns a Tablesaw table into Weka Instances.
   *
   * @param table	the table to convert
   * @return		the generated dataset
   */
  public static Instances tableToInstances(Table table) {
    Instances			result;
    ArrayList<Attribute>	atts;
    int				i;
    int				n;
    double[]			values;
    Row				row;

    // header
    atts = new ArrayList<Attribute>();
    for (i = 0; i < table.columnCount(); i++) {
      if (isNumeric(table.column(i).type()))
        atts.add(new Attribute(table.column(i).name()));
      else if (isDateLike(table.column(i).type()))
        atts.add(new Attribute(table.column(i).name(), "yyyy-MM-dd HH:mm:ss.SSS"));
      else
        atts.add(new Attribute(table.column(i).name(), (List<String>) null));
    }
    result = new Instances(table.name(), atts, table.rowCount());

    // data
    for (n = 0; n < table.rowCount(); n++) {
      values = new double[result.numAttributes()];
      row    = table.row(n);
      for (i = 0; i < row.columnCount(); i++) {
        values[i] = Utils.missingValue();
	if (result.attribute(i).isDate()) {
          if (table.column(i).type() == ColumnType.LOCAL_DATE_TIME)
	    values[i] = Date.from(row.getDateTime(i).toInstant(ZoneOffset.UTC)).getTime();
          else if (table.column(i).type() == ColumnType.LOCAL_DATE)
	    values[i] = row.getDate(i).toEpochDay() * 24 * 60 * 60 * 1000;
          else if (table.column(i).type() == ColumnType.LOCAL_DATE)
	    values[i] = row.getTime(i).toNanoOfDay() / 1000 / 1000;
	}
	else if (result.attribute(i).isNumeric()) {
          values[i] = row.getDouble(i);
	}
	else {
          values[i] = result.attribute(i).addStringValue(row.getString(i));
	}
      }
      result.add(new DenseInstance(1.0, values));
    }

    return result;
  }

  /**
   * Turns Weka Instances into a Tablesaw table.
   *
   * @param instances	the instances to convert
   * @return		the generated table
   */
  public static Table instancesToTable(Instances instances) {
    Table		result;
    Attribute		att;
    Instance 		inst;
    List<Column<?>>	columns;
    int			i;
    int			n;
    Row			row;
    ZoneId		zoneId;

    // header
    columns = new ArrayList<Column<?>>();
    for (i = 0; i < instances.numAttributes(); i++) {
      att = instances.attribute(i);
      if (att.isDate())
        columns.add(DateTimeColumn.create(att.name()));
      else if (att.isNumeric())
        columns.add(DoubleColumn.create(att.name()));
      else if (att.isNominal())
        columns.add(StringColumn.create(att.name()));
      else
        columns.add(TextColumn.create(att.name()));
    }
    result = Table.create(instances.relationName(), columns);

    // data
    zoneId = ZoneId.of("UTC");
    for (n = 0; n < instances.numInstances(); n++) {
      inst = instances.instance(n);
      row  = result.appendRow();
      for (i = 0; i < instances.numAttributes(); i++) {
	att = instances.attribute(i);
	if (att.isDate())
	  row.setDateTime(i, LocalDateTime.ofInstant(new Date((long) inst.value(i)).toInstant(), zoneId));
	else if (att.isNumeric())
	  row.setDouble(i, inst.value(i));
	else if (att.isNominal())
	  row.setString(i, inst.stringValue(i));
	else
	  row.setText(i, inst.stringValue(i));
      }
    }

    return result;
  }
}
