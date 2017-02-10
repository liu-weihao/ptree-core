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
package com.dx.ss.plugins.ptree.generate.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import com.dx.ss.plugins.ptree.config.xml.OutputUtilities;

public class Method {

	private JavaVisibility visibility = JavaVisibility.PUBLIC;
	
    /** The body lines. */
    private List<String> bodyLines;

    /** The return type. */
    private FullyQualifiedJavaType returnType;

    /** The name. */
    private String name;

    /** The parameters. */
    private List<Parameter> parameters;

    public JavaVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(JavaVisibility visibility) {
		this.visibility = visibility;
	}

	/**
     * Instantiates a new method.
     */
    public Method() {
        // use a default name to avoid malformed code
        this("bar"); //$NON-NLS-1$
    }
    
    /**
     * Instantiates a new method.
     *
     * @param name
     *            the name
     */
    public Method(String name) {
        super();
        bodyLines = new ArrayList<String>();
        parameters = new ArrayList<Parameter>();
        this.name = name;
    }
    
    /**
     * Gets the body lines.
     *
     * @return Returns the bodyLines.
     */
    public List<String> getBodyLines() {
        return bodyLines;
    }

    /**
     * Adds the body line.
     *
     * @param line
     *            the line
     */
    public void addBodyLine(String line) {
        bodyLines.add(line);
    }

    /**
     * Adds the body line.
     *
     * @param index
     *            the index
     * @param line
     *            the line
     */
    public void addBodyLine(int index, String line) {
        bodyLines.add(index, line);
    }

    /**
     * Adds the body lines.
     *
     * @param lines
     *            the lines
     */
    public void addBodyLines(Collection<String> lines) {
        bodyLines.addAll(lines);
    }

    /**
     * Adds the body lines.
     *
     * @param index
     *            the index
     * @param lines
     *            the lines
     */
    public void addBodyLines(int index, Collection<String> lines) {
        bodyLines.addAll(index, lines);
    }

    /**
     * Gets the formatted content.
     *
     * @param indentLevel
     *            the indent level
     * @param interfaceMethod
     *            the interface method
     * @return the formatted content
     */
    public String getFormattedContent(int indentLevel, boolean interfaceMethod) {
        StringBuilder sb = new StringBuilder();

        OutputUtilities.javaIndent(sb, indentLevel);

        if (interfaceMethod) {
        	sb.append("public ");
        } else {
            sb.append(getVisibility().getValue());

        }

        if (getReturnType() == null) {
            sb.append("void"); //$NON-NLS-1$
        } else {
            sb.append(getReturnType().getSimpleName());
        }
        sb.append(' ');

        sb.append(getName());
        sb.append('(');

        boolean comma = false;
        for (Parameter parameter : getParameters()) {
            if (comma) {
                sb.append(", ");
            } else {
                comma = true;
            }

            sb.append(parameter.getFormattedContent());
        }

        sb.append(')');

        // if no body lines, then this is an abstract/interface method
        if (bodyLines.size() == 0) {
            sb.append(';');
        } else {
            sb.append(" {"); //$NON-NLS-1$
            indentLevel++;

            ListIterator<String> listIter = bodyLines.listIterator();
            while (listIter.hasNext()) {
                String line = listIter.next();
                if (line.startsWith("}")) { //$NON-NLS-1$
                    indentLevel--;
                }

                OutputUtilities.newLine(sb);
                OutputUtilities.javaIndent(sb, indentLevel);
                sb.append(line);

                if ((line.endsWith("{") && !line.startsWith("switch")) //$NON-NLS-1$ //$NON-NLS-2$
                        || line.endsWith(":")) { //$NON-NLS-1$
                    indentLevel++;
                }

                if (line.startsWith("break")) { //$NON-NLS-1$
                    // if the next line is '}', then don't outdent
                    if (listIter.hasNext()) {
                        String nextLine = listIter.next();
                        if (nextLine.startsWith("}")) { //$NON-NLS-1$
                            indentLevel++;
                        }

                        // set back to the previous element
                        listIter.previous();
                    }
                    indentLevel--;
                }
            }

            indentLevel--;
            OutputUtilities.newLine(sb);
            OutputUtilities.javaIndent(sb, indentLevel);
            sb.append('}');
        }

        return sb.toString();
    }

    
    public FullyQualifiedJavaType getReturnType() {
		return returnType;
	}

	public void setReturnType(FullyQualifiedJavaType returnType) {
		this.returnType = returnType;
	}

	/**
     * Gets the name.
     *
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the parameters.
     *
     * @return the parameters
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Adds the parameter.
     *
     * @param parameter
     *            the parameter
     */
    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
    }

    /**
     * Adds the parameter.
     *
     * @param index
     *            the index
     * @param parameter
     *            the parameter
     */
    public void addParameter(int index, Parameter parameter) {
        parameters.add(index, parameter);
    }

}
