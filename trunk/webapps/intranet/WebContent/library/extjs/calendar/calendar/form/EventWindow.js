
Ext.define('Extensible.calendar.form.EventWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.extensible.eventeditwindow',
    
    requires: [
        'Ext.form.Panel',
        'Extensible.calendar.data.EventModel',
        'Extensible.calendar.data.EventMappings'
    ],
    
    // Locale configs
    titleTextAdd: 'Agregar Evento',
    titleTextEdit: 'Editar Evento',
    width: 600,
    labelWidth: 65,
    detailsLinkText: 'Editar Detalles...',
    savingMessage: 'Guardando cambios...',
    deletingMessage: 'Eliminando evento...',
    saveButtonText: 'Guardar',
    deleteButtonText: 'Eliminar',
    cancelButtonText: 'Cancelar',
    titleLabelText: 'Titulo',
    datesLabelText: 'Desde',
    calendarLabelText: 'Calendario',
    
    // General configs
    closeAction: 'hide',
    modal: false,
    resizable: false,
    constrain: true,
    buttonAlign: 'left',
    editDetailsLinkClass: 'edit-dtl-link',
    enableEditDetails: true,
    bodyStyle: 'padding: 8px 10px 5px;',
    
    formPanelConfig: {
        border: false
    },
    
    // private
    initComponent: function(){
        this.addEvents({
            /**
             * @event eventadd
             * Fires after a new event is added
             * @param {Extensible.calendar.form.EventWindow} this
             * @param {Extensible.calendar.data.EventModel} rec The new {@link Extensible.calendar.data.EventModel record} that was added
             * @param {Ext.Element} el The target element
             */
            eventadd: true,
            /**
             * @event eventupdate
             * Fires after an existing event is updated
             * @param {Extensible.calendar.form.EventWindow} this
             * @param {Extensible.calendar.data.EventModel} rec The new {@link Extensible.calendar.data.EventModel record} that was updated
             * @param {Ext.Element} el The target element
             */
            eventupdate: true,
            /**
             * @event eventdelete
             * Fires after an event is deleted
             * @param {Extensible.calendar.form.EventWindow} this
             * @param {Extensible.calendar.data.EventModel} rec The new {@link Extensible.calendar.data.EventModel record} that was deleted
             * @param {Ext.Element} el The target element
             */
            eventdelete: true,
            /**
             * @event eventcancel
             * Fires after an event add/edit operation is canceled by the user and no store update took place
             * @param {Extensible.calendar.form.EventWindow} this
             * @param {Extensible.calendar.data.EventModel} rec The new {@link Extensible.calendar.data.EventModel record} that was canceled
             * @param {Ext.Element} el The target element
             */
            eventcancel: true,
            /**
             * @event editdetails
             * Fires when the user selects the option in this window to continue editing in the detailed edit form
             * (by default, an instance of {@link Extensible.calendar.form.EventDetails}. Handling code should hide this window
             * and transfer the current event record to the appropriate instance of the detailed form by showing it
             * and calling {@link Extensible.calendar.form.EventDetails#loadRecord loadRecord}.
             * @param {Extensible.calendar.form.EventWindow} this
             * @param {Extensible.calendar.data.EventModel} rec The {@link Extensible.calendar.data.EventModel record} that is currently being edited
             * @param {Ext.Element} el The target element
             */
            editdetails: true
        });
        
        this.fbar = this.getFooterBarConfig();
        
        this.callParent(arguments);
    },
    
    getFooterBarConfig: function() {
        var cfg = ['->', {
                text: this.saveButtonText,
                itemId: this.id + '-save-btn',
                disabled: false,
                handler: this.onSave, 
                scope: this
            },{
                text: this.deleteButtonText, 
                itemId: this.id + '-delete-btn',
                disabled: false,
                handler: this.onDelete,
                scope: this,
                hideMode: 'offsets' // IE requires this
            },{
                text: this.cancelButtonText,
                itemId: this.id + '-cancel-btn',
                disabled: false,
                handler: this.onCancel,
                scope: this
            }];
        
        if(this.enableEditDetails !== false){
            cfg.unshift({
                xtype: 'tbtext',
                itemId: this.id + '-details-btn',
                text: '<a href="#" class="' + this.editDetailsLinkClass + '">' + this.detailsLinkText + '</a>'
            });
        }
        return cfg;
    },
    
    // private
    onRender : function(ct, position){
    	Evento.newEvento.window.show();
        /*this.formPanel = Ext.create('Ext.FormPanel', Ext.applyIf({
            fieldDefaults: {
                labelWidth: this.labelWidth
            },
            items: this.getFormItemConfigs()
        }, this.formPanelConfig));
        
        this.add(this.formPanel);
        
        this.callParent(arguments);*/
    },
    
    getFormItemConfigs: function() {
        var items = [{
            xtype: 'textfield',
            itemId: this.id + '-title',
            name: Extensible.calendar.data.EventMappings.Title.name,
            fieldLabel: this.titleLabelText,
            anchor: '100%'
        },{
            xtype: 'extensible.daterangefield',
            itemId: this.id + '-dates',
            name: 'dates',
            anchor: '95%',
            singleLine: true,
            fieldLabel: this.datesLabelText
        }];
        
        if(this.calendarStore){
            items.push({
                xtype: 'extensible.calendarcombo',
                itemId: this.id + '-calendar',
                name: Extensible.calendar.data.EventMappings.CalendarId.name,
                anchor: '100%',
                fieldLabel: this.calendarLabelText,
                store: this.calendarStore
            });
        }
        
        return items;
    },

    // private
    afterRender: function(){
        this.callParent(arguments);
		
		this.el.addCls('ext-cal-event-win');
        
        this.initRefs();
        
        // This junk spacer item gets added to the fbar by Ext (fixed in 4.0.2)
        var junkSpacer = this.getDockedItems('toolbar')[0].items.items[0];
        if (junkSpacer.el.hasCls('x-component-default')) {
            Ext.destroy(junkSpacer);
        }
    },
    
    initRefs: function() {
        // toolbar button refs
        this.saveButton = this.down('#' + this.id + '-save-btn');
        this.deleteButton = this.down('#' + this.id + '-delete-btn');
        this.cancelButton = this.down('#' + this.id + '-cancel-btn');
        this.detailsButton = this.down('#' + this.id + '-details-btn');
        
        if (this.detailsButton) {
            this.detailsButton.getEl().on('click', this.onEditDetailsClick, this);
        }
        
        // form item refs
        this.titleField = this.down('#' + this.id + '-title');
        this.dateRangeField = this.down('#' + this.id + '-dates');
        this.calendarField = this.down('#' + this.id + '-calendar');
    },
    
    // private
    onEditDetailsClick: function(e){
        e.stopEvent();
        this.updateRecord(this.activeRecord, true);
        this.fireEvent('editdetails', this, this.activeRecord, this.animateTarget);
    },
	
	/**
     * Shows the window, rendering it first if necessary, or activates it and brings it to front if hidden.
	 * @param {Ext.data.Record/Object} o Either a {@link Ext.data.Record} if showing the form
	 * for an existing event in edit mode, or a plain object containing a StartDate property (and 
	 * optionally an EndDate property) for showing the form in add mode. 
     * @param {String/Element} animateTarget (optional) The target element or id from which the window should
     * animate while opening (defaults to null with no animation)
     * @return {Ext.Window} this
     */
    show: function(o, animateTarget){
    	Evento.newEvento.window.show();
    },
    
    // private
    roundTime: function(dt, incr){
        incr = incr || 15;
        var m = parseInt(dt.getMinutes());
        return dt.add('mi', incr - (m % incr));
    },
    
    // private
    onCancel: function(){
    	this.cleanup(true);
		this.fireEvent('eventcancel', this, this.animateTarget);
    },

    // private
    cleanup: function(hide){
        if (this.activeRecord) {
            this.activeRecord.reject();
        }
        delete this.activeRecord;
		
        if (hide===true) {
			// Work around the CSS day cell height hack needed for initial render in IE8/strict:
			//var anim = afterDelete || (Ext.isIE8 && Ext.isStrict) ? null : this.animateTarget;
            this.hide();
        }
    },
    
    updateRecord: function(record, keepEditing) {
    	var fields = record.fields,
            values = this.formPanel.getForm().getValues(),
            name,
            M = Extensible.calendar.data.EventMappings,
            obj = {};

        fields.each(function(f) {
            name = f.name;
            if (name in values) {
                obj[name] = values[name];
            }
        });
        
        var dates = this.dateRangeField.getValue();
        obj[M.StartDate.name] = dates[0];
        obj[M.EndDate.name] = dates[1];
        obj[M.IsAllDay.name] = dates[2];

        record.beginEdit();
        record.set(obj);
        
        if (!keepEditing) {
            record.endEdit();
        }

        return this;
    },
    
    // private
    onSave: function(){
    	if(!this.formPanel.form.isValid()){
            return;
        }
		if(!this.updateRecord(this.activeRecord)){
			this.onCancel();
			return;
		}
		//this.fireEvent(this.activeRecord.phantom ? 'eventadd' : 'eventupdate', this, this.activeRecord, this.animateTarget);
		
		var fields = this.activeRecord.fields;
		var name;
		var obj = {};
		var values = this.formPanel.getForm().getValues();
		fields.each(function(f) {
            name = f.name;
            if (name in values) {
                obj[name] = values[name];
                alert(values[name]);
            }
        });
		return false;
		this.formPanel.form.submit({
			url: 'eventos/save',
			waitMsg:'Guardando evento, espere por favor...',
			success: function(form, request) {
                var ob =Ext.decode(request.response.responseText);
                if(!ob.success) {
                	System.MessageBox.info(ob.value);
                } else {
                	Evento.grid.store.load();
                	System.MessageBox.info('Evento guardado con exito');
                	//Evento.newEvento.panel.getForm().reset(); 
                }
            },
            failure: function(form, request) {
                System.MessageBox.error("<b>Se ha producido un error. " );
            }
		});
    },
    
    // private
    onDelete: function(){
		this.fireEvent('eventdelete', this, this.activeRecord, this.animateTarget);
    }
});