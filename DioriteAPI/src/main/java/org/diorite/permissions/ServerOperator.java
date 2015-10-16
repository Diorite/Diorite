/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.permissions;

/**
 * Represent <strong>possible</strong> server operator (Op) that should have most of permissions by default.
 */
public interface ServerOperator extends Permissible
{
    /**
     * Check if this is representing server operator.
     *
     * @return true if this represent server operator.
     */
    boolean isOp();

    /**
     * Set if this is an op.
     *
     * @param op if this should be an op.
     *
     * @return true if state was changed.
     */
    boolean setOp(boolean op);

    @Override
    default boolean hasPermission(final Permission permission)
    {
        final PermissionsContainer pc = this.getPermissionsContainer();
        return (pc == null) ? permission.getDefaultLevel().getValue(this.isOp()) : pc.hasPermission(permission);
    }
}
