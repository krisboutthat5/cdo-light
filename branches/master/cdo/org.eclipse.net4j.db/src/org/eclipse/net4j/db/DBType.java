/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Kai Schlamp - bug 282976: [DB] Influence Mappings through EAnnotations
 */
package org.eclipse.net4j.db;

import org.eclipse.net4j.util.io.ExtendedDataInput;
import org.eclipse.net4j.util.io.ExtendedDataOutput;
import org.eclipse.net4j.util.io.IOUtil;
import org.eclipse.net4j.util.io.TMPUtil;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Enumerates the SQL data types that are compatible with the DB framework.
 *
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 */
public enum DBType
{
  BOOLEAN(16)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      return writeValueBoolean(out, resultSet, column, canBeNull);
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      return readValueBoolean(in, statement, column, canBeNull, getCode());
    }
  },

  BIT(-7)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      return writeValueBoolean(out, resultSet, column, canBeNull);
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      return readValueBoolean(in, statement, column, canBeNull, getCode());
    }
  },

  TINYINT(-6)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      byte value = resultSet.getByte(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeByte(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      byte value = in.readByte();
      statement.setByte(column, value);
      return value;
    }
  },

  SMALLINT(5)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      short value = resultSet.getShort(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeShort(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      short value = in.readShort();
      statement.setShort(column, value);
      return value;
    }
  },

  INTEGER(4)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      int value = resultSet.getInt(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeInt(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      int value = in.readInt();
      statement.setInt(column, value);
      return value;
    }
  },

  BIGINT(-5)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      long value = resultSet.getLong(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeLong(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      long value = in.readLong();
      statement.setLong(column, value);
      return value;
    }
  },

  FLOAT(6)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      float value = resultSet.getFloat(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeFloat(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      float value = in.readFloat();
      statement.setFloat(column, value);
      return value;
    }
  },

  REAL(7)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      float value = resultSet.getFloat(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeFloat(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      float value = in.readFloat();
      statement.setFloat(column, value);
      return value;
    }
  },

  DOUBLE(8)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      double value = resultSet.getDouble(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeDouble(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      double value = in.readDouble();
      statement.setDouble(column, value);
      return value;
    }
  },

  NUMERIC(2)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      throw new UnsupportedOperationException("SQL NULL has to be considered");
      // BigDecimal value = resultSet.getBigDecimal(column);
      // BigInteger valueUnscaled = value.unscaledValue();
      //
      // byte[] byteArray = valueUnscaled.toByteArray();
      // out.writeInt(byteArray.length);
      // out.write(byteArray);
      // out.writeInt(value.scale());
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      throw new UnsupportedOperationException("SQL NULL has to be considered");
      // byte[] bytes = in.readByteArray();
      // int scale = in.readInt();
      // BigInteger valueUnscaled = new BigInteger(bytes);
      // BigDecimal value = new BigDecimal(valueUnscaled, scale);
      //
      // // TODO: Read out the precision, scale information and bring the big decimal to the correct form.
      // statement.setBigDecimal(column, value);
    }
  },

  DECIMAL(3)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      throw new UnsupportedOperationException("SQL NULL has to be considered");
      // BigDecimal value = resultSet.getBigDecimal(column);
      // BigInteger valueUnscaled = value.unscaledValue();
      //
      // byte[] byteArray = valueUnscaled.toByteArray();
      // out.writeInt(byteArray.length);
      // out.write(byteArray);
      // out.writeInt(value.scale());
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      throw new UnsupportedOperationException("SQL NULL has to be considered");
      // byte[] bytes = in.readByteArray();
      // int scale = in.readInt();
      //
      // BigInteger valueUnscaled = new BigInteger(bytes);
      // BigDecimal value = new BigDecimal(valueUnscaled, scale);
      // statement.setBigDecimal(column, value);
    }
  },

  CHAR(1)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      String value = resultSet.getString(column);
      out.writeString(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      String value = in.readString();
      statement.setString(column, value);
      return value;
    }
  },

  VARCHAR(12)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      String value = resultSet.getString(column);
      out.writeString(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      String value = in.readString();
      statement.setString(column, value);
      return value;
    }
  },

  LONGVARCHAR(-1, "LONG VARCHAR") //$NON-NLS-1$
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      String value = resultSet.getString(column);
      out.writeString(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      String value = in.readString();
      statement.setString(column, value);
      return value;
    }
  },

  CLOB(2005)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      Clob value = resultSet.getClob(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      long length = value.length();
      Reader reader = value.getCharacterStream();

      try
      {
        out.writeLong(length);
        while (length-- > 0)
        {
          int c = reader.read();
          out.writeChar(c);
        }
      }
      finally
      {
        IOUtil.close(reader);
      }

      return null;
    }

    @Override
    public Object readValueWithResult(final ExtendedDataInput in, PreparedStatement statement, int column,
        boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      Reader reader;

      long length = in.readLong();
      if (length > 0)
      {
        reader = createFileReader(in, length);
      }
      else
      {
        reader = new Reader()
        {
          @Override
          public int read(char[] cbuf, int off, int len) throws IOException
          {
            return -1;
          }

          @Override
          public void close() throws IOException
          {
            // Do nothing
          }
        };
      }

      statement.setCharacterStream(column, reader, (int)length);
      // reader.close();
      return null;
    }

    private FileReader createFileReader(final ExtendedDataInput in, long length) throws IOException
    {
      FileWriter fw = null;

      try
      {
        final File tempFile = TMPUtil.createTempFile("clob_", ".tmp");
        tempFile.deleteOnExit();

        fw = new FileWriter(tempFile);

        Reader reader = new Reader()
        {
          @Override
          public int read(char[] cbuf, int off, int len) throws IOException
          {
            int read = 0;

            try
            {
              while (read < len)
              {
                cbuf[off++] = in.readChar();
                read++;
              }
            }
            catch (EOFException ex)
            {
              read = -1;
            }

            return read;
          }

          @Override
          public void close() throws IOException
          {
          }
        };

        IOUtil.copyCharacter(reader, fw, length);

        return new FileReader(tempFile)
        {
          @Override
          public void close() throws IOException
          {
            super.close();
            tempFile.delete();
          }
        };
      }
      finally
      {
        IOUtil.close(fw);
      }
    }
  },

  DATE(91)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      java.sql.Date value = resultSet.getDate(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      long longValue = value.getTime();
      out.writeLong(longValue);
      return longValue;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      long value = in.readLong();
      statement.setDate(column, new java.sql.Date(value));
      return value;
    }
  },

  TIME(92)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      java.sql.Time value = resultSet.getTime(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      long longValue = value.getTime();
      out.writeLong(longValue);
      return longValue;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      long value = in.readLong();
      statement.setTime(column, new java.sql.Time(value));
      return value;
    }
  },

  TIMESTAMP(93)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      java.sql.Timestamp value = resultSet.getTimestamp(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeLong(value.getTime());
      out.writeInt(value.getNanos());
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      long value = in.readLong();
      int nanos = in.readInt();
      java.sql.Timestamp timeStamp = new java.sql.Timestamp(value);
      timeStamp.setNanos(nanos);
      statement.setTimestamp(column, timeStamp);
      return timeStamp;
    }
  },

  BINARY(-2)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      byte[] value = resultSet.getBytes(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeInt(value.length);
      out.write(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      byte[] value = in.readByteArray();
      statement.setBytes(column, value);
      return value;
    }
  },

  VARBINARY(-3)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      byte[] value = resultSet.getBytes(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeInt(value.length);
      out.write(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      byte[] value = in.readByteArray();
      statement.setBytes(column, value);
      return value;
    }
  },

  LONGVARBINARY(-4, "LONG VARBINARY") //$NON-NLS-1$
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      byte[] value = resultSet.getBytes(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeInt(value.length);
      out.write(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      byte[] value = in.readByteArray();
      statement.setBytes(column, value);
      return value;
    }
  },

  BLOB(2004)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
        throws SQLException, IOException
    {
      Blob value = resultSet.getBlob(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      long length = value.length();
      InputStream stream = value.getBinaryStream();

      try
      {
        out.writeLong(length);
        IOUtil.copyBinary(stream, new ExtendedDataOutput.Stream(out), length);
      }
      finally
      {
        IOUtil.close(stream);
      }

      return null;
    }

    @Override
    public Object readValueWithResult(final ExtendedDataInput in, PreparedStatement statement, int column,
        boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      long length = in.readLong();
      InputStream value = null;

      if (length > 0)
      {
        value = createFileInputStream(in, length);
      }
      else
      {
        value = new ByteArrayInputStream(new byte[0]);
      }

      statement.setBinaryStream(column, value, (int)length);

      // XXX cannot close the input stream here, because
      // it is still used in executeBatch() later.
      // so maybe we could return it here and let the caller
      // collect and close the streams.
      return null;
    }

    private FileInputStream createFileInputStream(final ExtendedDataInput in, long length) throws IOException
    {
      FileOutputStream fos = null;

      try
      {
        final File tempFile = TMPUtil.createTempFile("blob_", ".tmp");
        tempFile.deleteOnExit();

        fos = new FileOutputStream(tempFile);
        IOUtil.copyBinary(new ExtendedDataInput.Stream(in), fos, length);

        return new FileInputStream(tempFile)
        {
          @Override
          public void close() throws IOException
          {
            super.close();
            tempFile.delete();
          }
        };
      }
      finally
      {
        IOUtil.close(fos);
      }
    }
  };

  private static final int BOOLEAN_NULL = -1;

  private static final int BOOLEAN_FALSE = 0;

  private static final int BOOLEAN_TRUE = 1;

  private int code;

  private String keyword;

  private DBType(int code, String keyword)
  {
    this.code = code;
    this.keyword = keyword;
  }

  private DBType(int code)
  {
    this(code, null);
  }

  public int getCode()
  {
    return code;
  }

  public String getKeyword()
  {
    return keyword == null ? super.toString() : keyword;
  }

  @Override
  public String toString()
  {
    return getKeyword();
  }

  /**
   * @since 3.0
   */
  public void writeValue(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
      throws SQLException, IOException
  {
    writeValueWithResult(out, resultSet, column, canBeNull);
  }

  /**
   * @since 4.1
   */
  public abstract Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
      throws SQLException, IOException;

  /**
   * @since 3.0
   */
  public void readValue(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull)
      throws SQLException, IOException
  {
    readValueWithResult(in, statement, column, canBeNull);
  }

  /**
   * @since 4.1
   */
  public abstract Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column,
      boolean canBeNull) throws SQLException, IOException;

  private static Boolean writeValueBoolean(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull)
      throws SQLException, IOException
  {
    boolean value = resultSet.getBoolean(column);
    if (canBeNull)
    {
      if (resultSet.wasNull())
      {
        out.writeByte(BOOLEAN_NULL);
        return null;
      }

      out.writeByte(value ? BOOLEAN_TRUE : BOOLEAN_FALSE);
      return value;
    }

    out.writeBoolean(value);
    return value;
  }

  private static Boolean readValueBoolean(ExtendedDataInput in, PreparedStatement statement, int column,
      boolean canBeNull, int sqlType) throws IOException, SQLException
  {
    if (canBeNull)
    {
      byte opcode = in.readByte();
      switch (opcode)
      {
      case BOOLEAN_NULL:
        statement.setNull(column, sqlType);
        return null;

      case BOOLEAN_FALSE:
        statement.setBoolean(column, false);
        return false;

      case BOOLEAN_TRUE:
        statement.setBoolean(column, true);
        return true;

      default:
        throw new IOException("Invalid boolean opcode: " + opcode);
      }
    }

    boolean value = in.readBoolean();
    statement.setBoolean(column, value);
    return value;
  }

  /**
   * @since 3.0
   */
  public static DBType getTypeByKeyword(String keyword)
  {
    DBType[] values = DBType.values();
    for (int i = 0; i < values.length; i++)
    {
      DBType dbType = values[i];
      if (dbType.getKeyword().equalsIgnoreCase(keyword))
      {
        return dbType;
      }
    }

    return null;
  }
}
