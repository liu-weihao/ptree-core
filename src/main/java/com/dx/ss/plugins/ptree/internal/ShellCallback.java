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
package com.dx.ss.plugins.ptree.internal;

import java.io.File;

/**
 * This interface defines methods that a shell should support to enable
 * the generator
 * to work. A "shell" is defined as the execution environment (i.e. an
 * Eclipse plugin, and Ant task, a NetBeans plugin, etc.)
 * 
 * The default ShellCallback that is very low function and does
 * not support the merging of Java files. The default shell callback is 
 * appropriate for use in well controlled environments where no changes
 * made to generated Java files.
 * 
 * @author Jeff Butler
 */
public interface ShellCallback {
    
    /**
     * This method is called to ask the shell to resolve a project/package combination into a directory on the file
     * system. This method is called repeatedly (once for each generated file), so it would be wise for an implementing
     * class to cache results.
     * 
     * The returned <code>java.io.File</code> object:
     * <ul>
     * <li>Must be a directory</li>
     * <li>Must exist</li>
     * </ul>
     * 
     * The default shell callback interprets both values as directories and simply concatenates the two values to
     * generate the default directory.
     *
     * @param targetProject
     *            the target project
     * @param targetPackage
     *            the target package
     * @return the directory (must exist)
     * @throws ShellException
     *             if the project/package cannot be resolved into a directory on the file system. In this case, the
     *             generator will not save the file it is currently working on. The generator will add the exception
     *             message to the list of warnings automatically.
     */
    File getDirectory(String targetProject, String targetPackage);

}
