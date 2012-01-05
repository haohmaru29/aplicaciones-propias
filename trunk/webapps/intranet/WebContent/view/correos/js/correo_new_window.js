Ext.onReady(function() {
	var panel1 = null;
	Ext.define('account', {
        extend: 'Ext.data.Model',
        fields: [ 'idusuarioServidorCorreo', 'usuarioCorreo' ]
    });
	
	Correo.newWindow.store= Ext.create('Ext.data.JsonStore', {
        model: 'account',
        autoLoad: false,
        proxy: {
              type: 'ajax',
              url: 'correo/myaccounts',
              method: 'post',
              timeout: 550000,
              reader: {
                  type: 'json',
                  root: 'data',
                  totalProperty : 'count'
              }
          },
          idProperty: 'idusuarioServidorCorreo',
          fields: ['idusuarioServidorCorreo' , 'usuarioCorreo' ]
    });
	
	panel1 = Ext.create('Ext.form.Panel', {
		waitMsgTarget: true,
		layout: 'absolute',
		border: false,
		renderTo : 'new_correo', 
		waitMsg:'Enviando...',
		items : [{
			x: 5,
            y: 5,
            xtype: 'combo',
            fieldLabel: 'Cuenta',
            displayField: 'usuarioCorreo',
            valueField: 'idusuarioServidorCorreo',
            name: 'servidor',
            multiSelect: false,
            editable: false,
            allowBlank: false,
            forceSelection: true,
            emptyText:'Seleccione cuenta...',
            store: Correo.newWindow.store,
            queryMode: 'remote',
            anchor: '-5'
		}, {
			x: 5,
            y: 35,
			xtype : 'textfield',
			fieldLabel : 'Destinatario',
			name : 'to',
			allowBlank: false,
			anchor: '-5'
		}, {
			x: 5,
            y: 65,
			xtype : 'textfield',
			fieldLabel : 'Asunto',
			name : 'subject',
			allowBlank: false,
			anchor: '-5'
		},{
			x:5,
            y: 95,
			xtype: 'filefield',
            id: 'form-file',
            emptyText: 'Seleccion archivo',
            fieldLabel: 'Adjunto',
            name: 'photo-path',
            buttonText: '',
            anchor: '-5',	
            buttonConfig: {
                iconCls: 'icon-email-attach'
            }
		}, {
			x:5,
            y: 125,
            xtype : 'textarea',
			hideLabel: true,
			anchor: '-5 -5',
			fieldLabel : 'body',
			name : 'body'
		}],
		buttons: [{
            text: 'Enviar',
            handler: function() {
            	if(panel1.getForm().isValid() ) {
                    panel1.getForm().submit({
                    	url: 'correo/sent',
                    	waitMsg:'Enviando...',
                        success: function(form, request) {
                            var ob =Ext.decode(request.response.responseText);
                            System.MessageBox.info(ob.value);
                            //panel1.getForm().reset();
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
            	panel1.getForm().reset();
            }
        }]
	});

	Correo.newWindow = new Ext.Window({
		title : 'Mis repositorios',
		closable : true,
		frame : false,
		border : false,
		width : 500,
		height : 400,
		minWidth: 300,
        minHeight: 200,
        layout: 'fit',
        plain:true,
		closeAction : 'hide',
		modal : true,
		items : [ panel1 ]
	});
});