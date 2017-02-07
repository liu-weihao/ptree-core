/**
 *    Copyright 2006-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.dx.ss.plugins.ptree.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.commons.lang3.StringUtils;

/**
 * This class holds methods useful for constructing custom classloaders.
 * 
 * @author Jeff Butler
 * 
 */
public class ClassloaderUtility {

    /**
     * Utility Class - No Instances
     */
    private ClassloaderUtility() {  }

    public static ClassLoader getCustomClassloader(String classpathEntry) {
        File file = null;
        try {
			if (StringUtils.isNotBlank(classpathEntry)) {
				file = new File(classpathEntry);
				if (file.exists()) {
					ClassLoader parent = Thread.currentThread().getContextClassLoader();
					URLClassLoader ucl = new URLClassLoader(new URL[] { file.toURI().toURL() }, parent);
					return ucl;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        return null;
    }
}
