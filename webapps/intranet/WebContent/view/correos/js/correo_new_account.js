Ext.onReady(function() {
	var newAccount = null;
	
	Ext.define('newAccount', {
        extend: 'Ext.data.Model',
        fields: [ 'idServidorCorreo', 'nombreServicio' ]
    }); 
	Ext.define('iconsServer', {
        extend: 'Ext.data.Model',
        fields: [ 'idIconos', 'nombreIconos' ]
    });    
	
	Correo.newAccount.store= Ext.create('Ext.data.JsonStore', {
        model: 'newAccount',
        autoLoad: false,
        proxy: {
              type: 'ajax',
              url: 'servidor/all',
              method: 'post',
              timeout: 550000,
              reader: {
                  type: 'json',
                  root: 'data',
                  totalProperty : 'count'
              }
          },
	        idProperty: 'idServidorCorreo',
	        fields: ['idServidorCorreo' , 'nombreServicio' ]
    });
	/*
	Correo.newAccount.storeIcons= Ext.create('Ext.data.JsonStore', {
        model: 'iconsServer',
        autoLoad: false,
        proxy: {
              type: 'ajax',
              url: 'icons/mail',
              method: 'post',
              timeout: 550000,
              reader: {
                  type: 'json',
                  root: 'data',
                  totalProperty : 'count'
              }
          },
	        idProperty: 'idIconos',
	        fields: ['idIconos', 'nombreIconos' ]
    });*/
	
	newAccount = Ext.create('Ext.form.Panel', {
		waitMsgTarget: true,
		layout: 'absolute',
		border: false,
		renderTo : 'new_correo', 
		waitMsg:'Enviando...',
		items : [{
			x: 5,
            y: 5,
            xtype: 'combo',
            fieldLabel: 'Servicio',
            displayField: 'nombreServicio',
            valueField: 'idServidorCorreo',
            name: 'servidor',
            multiSelect: false,
            editable: false,
            forceSelection: true,
            emptyText:'Seleccione servicio...',
            store: Correo.newAccount.store,
            queryMode: 'remote',
            anchor: '-5'
		} /*,{
			x: 5,
            y: 35,
            xtype: 'combo',
            fieldLabel: 'Icono',
            displayField: 'nombreIconos',
            valueField: 'idIconos',
            name: 'iconoServer',
            multiSelect: false,
            editable: false,
            forceSelection: true,
            emptyText:'Seleccione icono...',
            store: Correo.newAccount.storeIcons,
            queryMode: 'remote',
            anchor: '-5'
		}*/ ,{
			x: 5,
            y: 35,
			xtype : 'textfield',
			fieldLabel : 'Usuario',
			name : 'usuarioCorreo',
			allowBlank: false,
			anchor: '-5'
		}, {
			x: 5,
            y: 65,
			xtype : 'textfield',
			fieldLabel : 'Contrase\u00f1a',
			inputType: 'password',
			name : 'claveCorreo',
			allowBlank: false,
			anchor: '-5'
		}],
		buttons: [{
            text: 'Guardar',
            handler: function() {
            	if(newAccount.getForm().isValid() ) {
            		newAccount.getForm().submit({
            			url: 'correo/newAccount',
                    	waitMsg:'Guardando, espere por favor...',
                        success: function(form, request) {
                            var ob =Ext.decode(request.response.responseText);
                            if(!ob.success) {
                                System.MessageBox.error("<b>Error al guardar cuenta, favor intente mas tarde...");
                            } else {
                            	 Correo.tree.store.load();
                            	System.MessageBox.info("<b>Cuenta guardada con exito.</b>");
                            }
                            newAccount.getForm().reset();
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
            	newAccount.getForm().reset();
            }
        }]
	});

	Correo.newAccount.win = new Ext.Window({
		title : 'Crear nueva cuenta correo',
		closable : true,
		frame : false,
		border : false,
		width : 300,
		height : 250,
		minWidth: 150,
        minHeight: 100,
        layout: 'fit',
        plain:true,
		closeAction : 'hide',
		modal : true,
		items : [ newAccount ]
	});
	
});