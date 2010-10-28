/*
 *
 *     Copyright (C) 2010  David Fernandes
 *
 *                         Rua da Quinta Amarela, 60
 *                         4475-663 MAIA
 *                         PORTUGAL
 *
 *                         <daugfernandes@aim.com>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package teste;

/*

    teste::tmtEvent.java

 */
/**
 * A simple class for register events and its time
 * @author David Fernandes
 */
public class tmtEvent {

    boolean _isOpen;
    long _start;
    java.util.List<tmtEvent> _events;
    String _name;
    Object _bag;
    long _end;

    /**
     * Parameterless constructor
     */
    public tmtEvent() {
        _name = "";
        _start = System.currentTimeMillis();
        _events = new java.util.ArrayList<tmtEvent>();
        _bag = null;
        _isOpen = true;
    }

    /**
     * Constructor with parameters
     * @param p_name A description for the event
     * @param p_bag An object that can be attached. May be used to add custom information to the event.
     */
    public tmtEvent(String p_name, Object p_bag) {
        _name = p_name;
        _start = System.currentTimeMillis();
        _events = new java.util.ArrayList<tmtEvent>();
        _bag = p_bag;
        _isOpen = true;
    }

    /**
     * Selector
     * @return Event's description.
     */
    public String Name() {
        return _name;
    }

    /**
     * Selector
     * @return Custom object associated with the event.
     */
    public Object Bag() {
        return _bag;
    }

    /**
     * Start timestamp
     * @return Milliseconds
     */
    public long Start() {
        return _start;
    }

    /**
     * End timestamp
     * @return Milliseconds
     */
    public long End() {
        return _end;
    }

    /**
     * State of the event. True while operation Stop() is not used.
     * @return True is the event is still opened.
     */
    public boolean IsOpen() {
        return _isOpen;
    }
    
    /**
     * Stop the event.
     */
    public void Stop() {
        _end = System.currentTimeMillis();
        _isOpen = false;
    }

    /**
     * Return's the last event added to list of sub-events.
     * @return
     */
    public tmtEvent LastEvent() {
        if(_events.size()==0)
            return null;
        else
            return _events.get(_events.size()-1);
    }

    /**
     * Stops the last sub-event.
     */
    public void StopLastEvent() {
        tmtEvent le = LastEvent();
        if(le!=null) le.Stop();
    }

    /**
     * Selector
     * @return List of sub-events
     */
    public java.util.List<tmtEvent> Events() {
        return _events;
    }

    /**
     * Add an event to the list of sub-events
     * @param p_event
     */
    public void addEvent(tmtEvent p_event) {
        _events.add(p_event);
    }

    @Override public String toString() {
        return toString("");
    }

    /**
     * Customized toString(9 method returns kind-of XML
     * @param p_ident
     * @return
     */
    private String toString(String p_ident) {

        String ret=p_ident.concat("<event name=\"".concat(Name()).concat("\""));
        ret = ret.concat(" start=\"").concat(Long.toString(Start())).concat("\"");
        
        if(IsOpen()) {
            Stop();
            ret = ret.concat(" end=\"").concat(Long.toString(End())).concat("\"");
            ret = ret.concat(" duration=\"").concat(Long.toString(End()-Start())).concat("\"");
            ret = ret.concat(" status=\"OPEN\"");
        } else {
            ret = ret.concat(" end=\"").concat(Long.toString(End())).concat("\"");
            ret = ret.concat(" duration=\"").concat(Long.toString(End()-Start())).concat("\"");
            ret = ret.concat(" status=\"CLOSED\"");
        }

        if(Events().size()>0) {
            ret = ret.concat(">\n");
            for(tmtEvent ev : Events())
                ret = ret.concat(ev.toString(p_ident.concat("    ")));
            ret = ret.concat(p_ident.concat("</event>\n"));
        } else 
            ret = ret.concat(" />\n");

        return ret;
    }


}
