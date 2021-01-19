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
 * RowOperation.java
 * Copyright (C) 2021 University of Waikato, Hamilton, NZ
 */

package weka.core.tablesaw;

import tech.tablesaw.api.Table;
import weka.core.OptionHandler;

import java.io.Serializable;

/**
 * Interface for row operations.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public interface RowOperation
  extends Serializable, OptionHandler {

  /**
   * Returns a string describing this object.
   *
   * @return 		a description of the Loader suitable for
   * 			displaying in the explorer/experimenter gui
   */
  public String globalInfo();

  /**
   * Processes the rows.
   *
   * @param table	the table to work on
   * @return		the updated table
   */
  public abstract Table processRows(Table table);
}
