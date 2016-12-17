/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.inject.binder;

import java.lang.annotation.Annotation;

import org.diorite.inject.binder.qualifier.QualifierPattern;
import org.diorite.inject.binder.qualifier.QualifierPredicateOne;
import org.diorite.inject.binder.qualifier.QualifierPredicateOneDynamic;
import org.diorite.inject.binder.qualifier.QualifierPredicateThree;
import org.diorite.inject.binder.qualifier.QualifierPredicateThreeDynamic;
import org.diorite.inject.binder.qualifier.QualifierPredicateTwo;
import org.diorite.inject.binder.qualifier.QualifierPredicateTwoDynamic;

public interface Binder<T>
{
    BinderInstance<T> with(QualifierPattern a);

    void dynamic(DynamicProvider<T> dynamic);

    default BinderInstance<T> with()
    {
        return this.with(QualifierPattern.EMPTY);
    }

    default BinderInstance<T> with(Class<? extends Annotation> a)
    {
        return this.with(data -> data.getQualifier(a) != null);
    }

    default BinderInstance<T> with(Class<? extends Annotation> a, Class<? extends Annotation> b)
    {
        return this.with(data ->
                         {
                             if (data.getQualifier(a) == null)
                             {
                                 return false;
                             }
                             return (data.getQualifier(b) != null);
                         });
    }

    default BinderInstance<T> with(Class<? extends Annotation> a, Class<? extends Annotation> b, Class<? extends Annotation> c)
    {
        return this.with(data ->
                         {
                             if (data.getQualifier(a) == null)
                             {
                                 return false;
                             }
                             if (data.getQualifier(b) == null)
                             {
                                 return false;
                             }
                             return (data.getQualifier(c) != null);
                         });
    }

    default BinderInstance<T> with(Class<? extends Annotation> a, Class<? extends Annotation> b, Class<? extends Annotation> c,
                                   Class<? extends Annotation> d)
    {
        return this.with(data ->
                         {
                             if (data.getQualifier(a) == null)
                             {
                                 return false;
                             }
                             if (data.getQualifier(b) == null)
                             {
                                 return false;
                             }
                             if (data.getQualifier(c) == null)
                             {
                                 return false;
                             }
                             return (data.getQualifier(d) != null);
                         });
    }

    default BinderInstance<T> with(Class<? extends Annotation> a, Class<? extends Annotation> b, Class<? extends Annotation> c,
                                   Class<? extends Annotation> d, Class<? extends Annotation> e)
    {
        return this.with(data ->
                         {
                             if (data.getQualifier(a) == null)
                             {
                                 return false;
                             }
                             if (data.getQualifier(b) == null)
                             {
                                 return false;
                             }
                             if (data.getQualifier(c) == null)
                             {
                                 return false;
                             }
                             if (data.getQualifier(d) == null)
                             {
                                 return false;
                             }
                             return (data.getQualifier(e) != null);
                         });
    }

    default BinderInstance<T> with(Annotation a)
    {
        return this.with(data ->
                         {
                             Annotation aq = data.getQualifier(a.annotationType());
                             return ! ((aq == null) || ! aq.equals(a));
                         });
    }

    default BinderInstance<T> with(Annotation a, Annotation b)
    {
        return this.with(data ->
                         {
                             Annotation aq = data.getQualifier(a.annotationType());
                             if ((aq == null) || ! aq.equals(a))
                             {
                                 return false;
                             }
                             Annotation bq = data.getQualifier(b.annotationType());
                             return ! ((bq == null) || ! bq.equals(a));
                         });
    }

    default BinderInstance<T> with(Annotation a, Annotation b, Annotation c)
    {
        return this.with(data ->
                         {
                             Annotation aq = data.getQualifier(a.annotationType());
                             if ((aq == null) || ! aq.equals(a))
                             {
                                 return false;
                             }
                             Annotation bq = data.getQualifier(b.annotationType());
                             if ((bq == null) || ! bq.equals(a))
                             {
                                 return false;
                             }
                             Annotation cq = data.getQualifier(c.annotationType());
                             return ! ((cq == null) || ! cq.equals(a));
                         });
    }

    default BinderInstance<T> with(Annotation a, Annotation b, Annotation c, Annotation d)
    {
        return this.with(data ->
                         {
                             Annotation aq = data.getQualifier(a.annotationType());
                             if ((aq == null) || ! aq.equals(a))
                             {
                                 return false;
                             }
                             Annotation bq = data.getQualifier(b.annotationType());
                             if ((bq == null) || ! bq.equals(a))
                             {
                                 return false;
                             }
                             Annotation cq = data.getQualifier(c.annotationType());
                             if ((cq == null) || ! cq.equals(a))
                             {
                                 return false;
                             }
                             Annotation dq = data.getQualifier(d.annotationType());
                             return ! ((dq == null) || ! dq.equals(a));
                         });
    }

    default BinderInstance<T> with(Annotation a, Annotation b, Annotation c, Annotation d, Annotation e)
    {
        return this.with(data ->
                         {
                             Annotation aq = data.getQualifier(a.annotationType());
                             if ((aq == null) || ! aq.equals(a))
                             {
                                 return false;
                             }
                             Annotation bq = data.getQualifier(b.annotationType());
                             if ((bq == null) || ! bq.equals(a))
                             {
                                 return false;
                             }
                             Annotation cq = data.getQualifier(c.annotationType());
                             if ((cq == null) || ! cq.equals(a))
                             {
                                 return false;
                             }
                             Annotation dq = data.getQualifier(d.annotationType());
                             if ((dq == null) || ! dq.equals(a))
                             {
                                 return false;
                             }
                             Annotation eq = data.getQualifier(e.annotationType());
                             return ! ((eq == null) || ! eq.equals(a));
                         });
    }

    default <A extends Annotation> BinderInstance<T> with(Class<A> a, QualifierPredicateOne<A> predicate)
    {
        return this.with(data ->
                         {
                             A aq = data.getQualifier(a);
                             if (aq == null)
                             {
                                 return false;
                             }
                             return predicate.test(aq);
                         });
    }

    default <A extends Annotation, B extends Annotation> BinderInstance<T> with(Class<A> a, Class<B> b, QualifierPredicateTwo<A, B> predicate)
    {
        return this.with(data ->
                         {
                             A aq = data.getQualifier(a);
                             if (aq == null)
                             {
                                 return false;
                             }
                             B bq = data.getQualifier(b);
                             if (bq == null)
                             {
                                 return false;
                             }
                             return predicate.test(aq, bq);
                         });
    }

    default <A extends Annotation, B extends Annotation, C extends Annotation> BinderInstance<T> with(Class<A> a, Class<B> b, Class<C> c,
                                                                                                      QualifierPredicateThree<A, B, C> predicate)
    {
        return this.with(data ->
                         {
                             A aq = data.getQualifier(a);
                             if (aq == null)
                             {
                                 return false;
                             }
                             B bq = data.getQualifier(b);
                             if (bq == null)
                             {
                                 return false;
                             }
                             C cq = data.getQualifier(c);
                             if (cq == null)
                             {
                                 return false;
                             }
                             return predicate.test(aq, bq, cq);
                         });
    }

    default <A extends Annotation> void dynamic(Class<A> a, QualifierPredicateOneDynamic<T, A> predicate)
    {
        this.dynamic((obj, data) ->
                     {
                         A aq = data.getQualifier(a);
                         if (aq == null)
                         {
                             return null;
                         }
                         return predicate.test(obj, aq);
                     });
    }

    default <A extends Annotation, B extends Annotation> void dynamic(Class<A> a, Class<B> b, QualifierPredicateTwoDynamic<T, A, B> predicate)
    {
        this.dynamic((obj, data) ->
                     {
                         A aq = data.getQualifier(a);
                         if (aq == null)
                         {
                             return null;
                         }
                         B bq = data.getQualifier(b);
                         if (bq == null)
                         {
                             return null;
                         }
                         return predicate.test(obj, aq, bq);
                     });
    }

    default <A extends Annotation, B extends Annotation, C extends Annotation> void dynamic(Class<A> a, Class<B> b, Class<C> c,
                                                                                            QualifierPredicateThreeDynamic<T, A, B, C> predicate)
    {
        this.dynamic((obj, data) ->
                     {
                         A aq = data.getQualifier(a);
                         if (aq == null)
                         {
                             return null;
                         }
                         B bq = data.getQualifier(b);
                         if (bq == null)
                         {
                             return null;
                         }
                         C cq = data.getQualifier(c);
                         if (cq == null)
                         {
                             return null;
                         }
                         return predicate.test(obj, aq, bq, cq);
                     });
    }

}
