package cs3500.freecell.controller;

import java.io.IOException;

/**
 * Class to represent a bad appendable that fails when written to for testing purposes.
 */
public class BadAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("Fail!");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("Fail!");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("Fail!");
  }
}