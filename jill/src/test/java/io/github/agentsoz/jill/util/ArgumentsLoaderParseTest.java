package io.github.agentsoz.jill.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;


public class ArgumentsLoaderParseTest {

  private ByteArrayOutputStream out;
  private ByteArrayOutputStream err;

  private static final String exitStatus = "Exit status";

  protected static class ExitException extends SecurityException {
    private static final long serialVersionUID = 6476530573058049502L;

    public final int status;

    public ExitException(int status) {
      super("There is no escape!");
      this.status = status;
    }
  }

  private static class NoExitSecurityManager extends SecurityManager {
    @Override
    public void checkPermission(Permission perm) {
      // allow anything.
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
      // allow anything.
    }

    @Override
    public void checkExit(int status) {
      super.checkExit(status);
      throw new ExitException(status);
    }
  }

  /**
   * Common setup for all tests. Saves stderr and stdout to an output stream.
   * 
   * @throws Exception if something went wrong
   */
  @Before
  public void setUp() throws Exception {
    System.setSecurityManager(new NoExitSecurityManager());
    out = new ByteArrayOutputStream();
    err = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    System.setErr(new PrintStream(err));
  }

  /**
   * Common setup for all tests. Closes stderr and stdout streams.
   * 
   * @throws Exception if something went wrong
   */
  @After
  public void tearDown() throws Exception {
    System.setSecurityManager(null); // or save and restore original
    System.setOut(null);
    System.setErr(null);
    out.close();
    err.close();
  }

  @Test
  public void testParse() throws Exception {
    try {
      String[] args = null;
      ArgumentsLoader.parse(args);
    } catch (ExitException e) {
      assertEquals(exitStatus, 1, e.status);
    }
    try {
      String[] args = {};
      ArgumentsLoader.parse(args);
    } catch (ExitException e) {
      assertEquals(exitStatus, 1, e.status);
    }
    try {
      String[] args = {""};
      ArgumentsLoader.parse(args);
    } catch (ExitException e) {
      assertEquals(exitStatus, 1, e.status);
    }
    try {
      String[] args = {"--help"};
      ArgumentsLoader.parse(args);
    } catch (ExitException e) {
      assertEquals(exitStatus, 0, e.status);
    }
    try {
      String[] args = {"--config",
          "{programOutputFile:\"./test1.log\","
              + "agents:[{classname:io.github.agentsoz.jill.example.hanoi.Player, "
              + "args:[-discs, 9], " + "count: 1}]" + "}"};
      ArgumentsLoader.parse(args);
    } catch (ExitException e) {
      fail("Command line args could not be parsed: " + e.getMessage());
    }
    try {
      String configFile = getClass().getResource("config-example.txt").getFile();
      String[] args = {"--configfile", configFile};
      ArgumentsLoader.parse(args);
    } catch (ExitException e) {
      fail("Command line args could not be parsed: " + e.getMessage());
    }
    try {
      String configFile = getClass().getResource("config-example.txt").getFile();
      String[] args = {"--configfile", configFile, "--exit-on-idle", "true", "--exit-on-idle",
          "false", "--plan-selection-policy", "FIRST", "--plan-selection-policy", "RANDOM",
          "--plan-selection-policy", "LAST", "--plan-instances-limit", "10"};
      ArgumentsLoader.parse(args);
    } catch (ExitException e) {
      fail("Command line args could not be parsed: " + e.getMessage());
    }
  }

}
