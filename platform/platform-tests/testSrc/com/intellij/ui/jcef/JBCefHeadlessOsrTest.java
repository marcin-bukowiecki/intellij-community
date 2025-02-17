// Copyright 2000-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.ui.jcef;

import com.intellij.testFramework.ApplicationRule;
import com.intellij.testFramework.HeadlessRule;
import com.intellij.ui.scale.TestScaleHelper;
import com.intellij.util.Function;
import org.jetbrains.annotations.NotNull;
import org.junit.*;
import org.junit.rules.TestRule;

import javax.swing.*;
import java.awt.*;

import static com.intellij.ui.jcef.JBCefTestHelper.invokeAndWaitForLoad;

/**
 * Tests headless mode for an OSR browser.
 *
 * @author tav
 */
public class JBCefHeadlessOsrTest {
  @Rule public TestRule headless = new HeadlessRule();
  @ClassRule public static final ApplicationRule appRule = new ApplicationRule();

  @Before
  public void before() {
    TestScaleHelper.assumeStandalone();
    TestScaleHelper.setRegistryProperty("ide.browser.jcef.headless.enabled", "true");
    TestScaleHelper.setRegistryProperty("ide.browser.jcef.osr.enabled", "true");
  }

  @Test
  public void test() {
    JBCefBrowser browser = JBCefBrowser.createBuilder()
      .setOffScreenRendering(true)
      .setOSRHandlerFactory(new JBCefOSRHandlerFactory() {
        final Rectangle bounds = new Rectangle(1024, 768);

        @Override
        public @NotNull Function<JComponent, Rectangle> createScreenBoundsProvider() {
          return component -> bounds;
        }
      })
      .setUrl("chrome:version")
      .createBrowser();

    invokeAndWaitForLoad(browser, () -> browser.getCefBrowser().createImmediately());
  }
}
