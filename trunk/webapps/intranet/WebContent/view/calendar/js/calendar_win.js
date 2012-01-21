Ext.Loader.setConfig({
    enabled: true,
    disableCaching: false,
    paths: {
        'Extensible': 'library/extjs/calendar',
        'Extensible.example': '/'
    }
});

Ext.require([
     'Ext.data.proxy.Rest',
     'Extensible.calendar.data.MemoryCalendarStore',
     'Extensible.calendar.data.EventStore',
     'Extensible.calendar.CalendarPanel'
]);

Ext.onReady(function() {

	Calendario.win.calendarStore = Ext.create('Extensible.calendar.data.MemoryCalendarStore', {
        autoLoad: true,
        proxy: {
            type: 'ajax',
            url: 'calendario/usuario',
            noCache: false,
            reader: {
                type: 'json',
                root: 'data'
            }
        }
    });
	
	Calendario.win.eventStore = Ext.create('Extensible.calendar.data.EventStore', {
        autoLoad: true,
        proxy: {
            type: 'ajax',
            url: 'eventos/usuario',
            noCache: false,
            reader: {
                type: 'json',
                root: 'data'
            },
            writer: {
                type: 'json',
                nameProperty: 'mapping'
            },
            listeners: {
                exception: function(proxy, response, operation, options){
                    var msg = response.message ? response.message : Ext.decode(response.responseText).message;
                    Ext.Msg.alert('Server Error', msg);
                }
            }
        },
        listeners: {
            'write': function(store, operation){
                //var title = Ext.value(operation.records[0].data[Extensible.calendar.data.EventMappings.Title.name], '(No title)');
                switch(operation.action){
                    case 'create': 
                        //Extensible.example.msg('Add', 'Added "' + title + '"');
                    	//Evento.newEvento.window.show();
                        break;
                    case 'update':
                        //Extensible.example.msg('Update', 'Updated "' + title + '"');
                        break;
                    case 'destroy':
                        //Extensible.example.msg('Delete', 'Deleted "' + title + '"');
                        break;
                }
            }
        }
    });
	
	Calendario.win.render =  new Ext.Window({
		title : 'Semana actual',
		closable : true,
		frame : false,
		border : false,
		width : 900,
		height : 600,
		minWidth: 300,
        minHeight: 200,
        layout: 'fit',
        plain:true,
		closeAction : 'hide',
		modal : true,
		items : [{
			 xtype: 'extensible.calendarpanel',
			 eventStore: Calendario.win.eventStore,
		     calendarStore: Calendario.win.calendarStore,
		     weekText: 'Semana',
		     showDayView: false,
		     activeItem: 0,
		     showMonthView: false,
		     multiWeekViewCfg: {
	            weekCount: 3
		     },
		     weekViewCfg: {
	            dayCount: 5,
	            startDay: 1,
	            startDayIsStatic: true,
	            showHourSeparator: false,
	            viewStartHour: 6,
	            viewEndHour: 20,
	            scrollStartHour: 8,
	            hourHeight: 84,
	            ddIncrement: 10,
	            minEventDisplayMinutes: 10
	        },
	        listeners: {
                'eventadd': {
                    fn: function(cp, rec){
                    	var x = 0;
                    	alert(rec.data);
                    	alert(rec.data.length);
                    	alert(rec.data[1]);
                    	for(x=0; x< rec.data.length ; x++) {
                    		alert(rec.data[x]);
                    	}
                    	//alert('Event '+ rec.data[Ext.ensible.cal.EventMappings.Title.name] +' was updated');
                    	//alert(rec.data[Ext.ensible.cal.EventMappings.StartDate.name]);
                        //this.showMsg('Event '+ rec.data[Ext.ensible.cal.EventMappings.Title.name] +' was added');
                    },
                    scope: this
                },
                'eventupdate': {
                    fn: function(cp, rec){
                    	alert(1);
                        //this.showMsg('Event '+ rec.data[Ext.ensible.cal.EventMappings.Title.name] +' was updated');
                    },
                    scope: this
                },
                'eventdelete': {
                    fn: function(cp, rec){
                    	alert(2);
                        //this.eventStore.remove(rec);
                        //this.showMsg('Event '+ rec.data[Ext.ensible.cal.EventMappings.Title.name] +' was deleted');
                    },
                    scope: this
                },
                'eventcancel': {
                    fn: function(cp, rec){
                        // edit canceled
                    	alert(3);
                    },
                    scope: this
                },
                'viewchange': {
                    fn: function(p, vw, dateInfo){
                        if(this.editWin){
                            this.editWin.hide();
                        };
                        if(dateInfo !== null){
                            // will be null when switching to the event edit form so ignore
                            //Ext.getCmp('app-nav-picker').setValue(dateInfo.activeDate);
                            //this.updateTitle(dateInfo.viewStart, dateInfo.viewEnd);
                        }
                    },
                    scope: this
                },
                'rangeselect': {
                    fn: function(vw, dates, onComplete){
                        //this.clearMsg();
                    	alert('range select');
                    },
                    scope: this
                },
                'eventmove': {
                    fn: function(vw, rec){
                        rec.commit();
                        //var time = rec.data[Calendario.win.eventStore.IsAllDay.name] ? '' : ' \\a\\t g:i a';
                        //alert(time);
                        /*this.showMsg('Event '+ rec.data[Ext.ensible.cal.EventMappings.Title.name] +' was moved to '+
                            rec.data[Ext.ensible.cal.EventMappings.StartDate.name].format('F jS'+time));*/
                    },
                    scope: this
                },
                'eventresize': {
                    fn: function(vw, rec){
                        rec.commit();
                        //this.showMsg('Event '+ rec.data[Ext.ensible.cal.EventMappings.Title.name] +' was updated');
                    },
                    scope: this
                },
                'eventdelete': {
                    fn: function(win, rec){
                        //this.eventStore.remove(rec);
                    	//this.showMsg('Event '+ rec.data[Ext.ensible.cal.EventMappings.Title.name] +' was deleted');
                    },
                    scope: this
                },
                'initdrag': {
                    fn: function(vw){
                        /*
                    	if(this.editWin && this.editWin.isVisible()){
                            //this.editWin.hide();
                        }
                        */
                    },
                    scope: this
                }
            }
		}]
	});
});