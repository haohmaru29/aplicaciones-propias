Ext.onReady(function() {
	 Ext.apply(Ext.form.field.VTypes, {
	        daterange: function(val, field) {
	            var date = field.parseDate(val);

	            if (!date) {
	                return false;
	            }
	            if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
	                var start = field.up('form').down('#' + field.startDateField);
	                start.setMaxValue(date);
	                start.validate();
	                this.dateRangeMax = date;
	            }
	            else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
	                var end = field.up('form').down('#' + field.endDateField);
	                end.setMinValue(date);
	                end.validate();
	                this.dateRangeMin = date;
	            }
	            return true;
	        },
	        daterangeText: 'Fecha inicial debe ser menor a la Fecha final'
	 });
	
	
	Ext.define('calendarios', {
        extend: 'Ext.data.Model',
        fields: [ 'nombreCalendario', 'idCalendario' ]
    });
	
	Evento.newEvento.store = Ext.create('Ext.data.JsonStore', {
        model: 'account',
        autoLoad: false,
        proxy: {
              type: 'ajax',
              url: 'calendario/usuario',
              method: 'post',
              timeout: 550000,
              reader: {
                  type: 'json',
                  root: 'data',
                  totalProperty : 'count'
              }
          },
          idProperty: 'idCalendario',
          fields: ['nombreCalendario' , 'idCalendario' ]
    });
	
	Evento.newEvento.panel = Ext.create('Ext.form.Panel', {
		waitMsgTarget: true,
		layout: 'absolute',
		border: false,
		renderTo : 'new_correo', 
		waitMsg:'Enviando...',
		items : [ {
			x: 5,
            y: 35,
			xtype : 'textfield',
			fieldLabel : 'Titulo',
			name : 'titulo',
			allowBlank: false,
			anchor: '-5'
		} , {
			x: 5,
            y: 65,
            xtype: 'combo',
            fieldLabel: 'Calendario',
            displayField: 'nombreCalendario',
            valueField: 'idCalendario',
            name: 'calendario',
            multiSelect: false,
            editable: false,
            allowBlank: false,
            forceSelection: true,
            emptyText:'Seleccione calendario...',
            store: Evento.newEvento.store,
            queryMode: 'remote',
            anchor: '-5'
		} ,{
			x: 5,
            y: 95,
			xtype : 'datefield',
			fieldLabel : 'Fecha Inicio',
			name : 'fechaInicio',
			allowBlank: false,
			id: 'startdt',
            vtype: 'daterange',
            endDateField: 'enddt',
			anchor: '-5'
		}, {
			x: 5,
            y: 125,
			xtype : 'datefield',
			fieldLabel : 'Fecha Termino',
			name : 'fechaTermino',
			allowBlank: false,
			id: 'enddt',
            vtype: 'daterange',
            startDateField: 'startdt',
			anchor: '-5'
		}, {
			x:5,
            y: 155,
            xtype : 'timefield',
			fieldLabel : 'Hora Inicio',
			name : 'horaInicio',
			allowBlank: false,
			minValue: '12:00am',
            maxValue: '12:00pm',
			anchor: '-5'
		},{
			x:5,
            y: 185,
            xtype : 'timefield',
			fieldLabel : 'Hora Termino',
			name : 'horaTermino',
			allowBlank: false,
			minValue: '12:00am',
            maxValue: '12:00pm',
			anchor: '-5'
		}, {
			x:5,
            y: 215,
            xtype : 'textfield',
			hideLabel: false,
			anchor: '-5',
			fieldLabel : 'Lugar',
			name : 'lugar'
		}],
		buttons: [{
            text: 'Crear evento',
            handler: function() {
            	if(Evento.newEvento.panel.getForm().isValid() ) {
            		Evento.newEvento.panel.getForm().submit({
                    	//url: 'correo/sent',
                    	waitMsg:'Enviando...',
                        success: function(form, request) {
                            var ob =Ext.decode(request.response.responseText);
                            System.MessageBox.info(ob.value);
                        },
                        failure: function(form, request) {
                            System.MessageBox.error("<b>Se ha producido un error. " );
                        }
                    });
                }
            }
        },{
            text: 'Limpiar',
            handler: function() {
            	Evento.newEvento.panel.getForm().reset();
            }
        }]
	});
	
	Evento.newEvento.window =  new Ext.Window({
		title : 'Nuevo Evento',
		closable : true,
		frame : false,
		border : false,
		width : 500,
		height : 600,
		minWidth: 300,
        minHeight: 200,
        layout: 'fit',
        plain:true,
		closeAction : 'hide',
		modal : true,
		items : [ Evento.newEvento.panel ]
	});
});