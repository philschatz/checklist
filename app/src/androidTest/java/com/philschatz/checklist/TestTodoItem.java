/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015 Miikka Andersson
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.philschatz.checklist;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * JUnit tests to verify functionality of ToDoItem class.
 */
public class TestTodoItem extends TestCase {
    private final Date CURRENT_DATE = new Date();
    private final String TEXT_BODY = "This is some text";
    private final boolean REMINDER_OFF = false;
    private final boolean REMINDER_ON = true;

    /**
     * Check we can construct a ToDoItem object using the three parameter constructor
     */
    public void testThreeParameterConstructor() {
        ToDoItem toDoItem = getToDoItem(REMINDER_OFF);
        assertEquals(TEXT_BODY, toDoItem.getTitle());
        assertEquals(null, toDoItem.getRemindAt());
    }

    /**
     * Ensure we can marshall ToDoItem objects to Json
     */
    public void testObjectMarshallingToJson() {
        ToDoItem toDoItem = getToDoItem(REMINDER_ON);

        try {
            JSONObject json = toDoItem.toJSON();
            assertEquals(TEXT_BODY, json.getString("todotext"));
            assertEquals(REMINDER_ON, json.getBoolean("todoreminder"));
            assertEquals(String.valueOf(CURRENT_DATE.getTime()), json.getString("tododate"));
        } catch (JSONException e) {
            fail("Exception thrown during test execution: " + e.getMessage());
        }
    }

    /**
     * Ensure we can create ToDoItem objects from Json data by using the json constructor
     */
    public void testObjectUnmarshallingFromJson() {
        ToDoItem originalItem = getToDoItem(REMINDER_OFF);

        try {
            JSONObject json = originalItem.toJSON();
            ToDoItem itemFromJson = new ToDoItem(json);

            assertEquals(originalItem.getTitle(), itemFromJson.getTitle());
            assertEquals(originalItem.getRemindAt(), itemFromJson.getRemindAt());
            assertEquals(originalItem.getIdentifier(), itemFromJson.getIdentifier());

        } catch (JSONException e) {
            fail("Exception thrown during test execution: " + e.getMessage());
        }
    }

    private ToDoItem getToDoItem(boolean hasReminder) {
        ToDoItem item = new ToDoItem();
        item.setTitle(TEXT_BODY);
        if (hasReminder) {
            item.setRemindAt(CURRENT_DATE);
        }
        return item;
    }
}
