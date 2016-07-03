/*
 * THPPS
 * Copyright © 2016 - Santiago Saavedra López <ssaavedra@gpul.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.ssaavedra.thpps;

import android.app.assist.AssistStructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class WindowNavigator {
    private final AssistStructure.WindowNode m_windowNode;

    public WindowNavigator(AssistStructure.WindowNode windowNode) {
        m_windowNode = windowNode;
    }

    public Iterator<CharSequence>
    getAllTexts() {
        return getAllTextsFrom(m_windowNode.getRootViewNode());
    }

    private Iterator<CharSequence>
    getAllTextsFrom(final AssistStructure.ViewNode v) {
        if (v.getChildCount() > 0) {
            // Non-final node
            List<Iterator<CharSequence>> list = new ArrayList<>();

            for (int i = 0; i < v.getChildCount(); i++) {
                AssistStructure.ViewNode child = v.getChildAt(i);
                Iterator<CharSequence> texts = getAllTextsFrom(child);
                list.add(texts);
            }

            return joinIteratorList(list);
        } else {
            // Final node
            return new SingleElementIterator<>(v.getText());
        }
    }

    private static Iterator<CharSequence>
    joinIteratorList(List<Iterator<CharSequence>> list) {
        LinkedList<CharSequence> result = new LinkedList<>();
        for (Iterator<CharSequence> i : list) {
            while (i.hasNext()) {
                result.add(i.next());
                if(result.peekLast() == null)
                    result.removeLast();
            }
        }

        return result.iterator();
    }

    private static class SingleElementIterator<T> implements
            Iterator<T> {
        private T elt;
        private boolean first = true;

        public SingleElementIterator(T elt) {
            this.elt = elt;
        }

        @Override
        public boolean hasNext() {
            return first;
        }

        @Override
        public T next() {
            if (!first) return null;
            first = false;
            return elt;
        }

        @Override
        public void remove() {
        }
    }
}
