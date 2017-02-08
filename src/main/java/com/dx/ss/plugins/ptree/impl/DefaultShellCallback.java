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
package com.dx.ss.plugins.ptree.impl;

import java.io.File;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.dx.ss.plugins.ptree.internal.ShellCallback;

/**
 * The Class DefaultShellCallback.
 *
 * @author Jeff Butler
 */
public class DefaultShellCallback implements ShellCallback {
    

    /**
     * Instantiates a new default shell callback.
     */
    public DefaultShellCallback() {
        super();
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.ShellCallback#getDirectory(java.lang.String, java.lang.String)
     */
    public File getDirectory(String targetProject, String targetPackage){
        // targetProject is interpreted as a directory that must exist
        //
        // targetPackage is interpreted as a sub directory, but in package
        // format (with dots instead of slashes). The sub directory will be
        // created
        // if it does not already exist

        File project = new File(targetProject);
        if (!project.isDirectory()) return null;

        StringBuilder sb = new StringBuilder();
        if(!StringUtils.isBlank(targetPackage)){
	        StringTokenizer st = new StringTokenizer(targetPackage, "."); //$NON-NLS-1$
	        while (st.hasMoreTokens()) {
	            sb.append(st.nextToken());
	            sb.append(File.separatorChar);
	        }
        }

        File directory = new File(project, sb.toString());
        if (!directory.isDirectory()) {
            boolean rc = directory.mkdirs();
            if (!rc) return null;
        }

        return directory;
    }

}
