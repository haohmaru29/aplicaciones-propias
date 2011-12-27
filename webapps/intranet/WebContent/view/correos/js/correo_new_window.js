Ext.onReady(function() {
	var panel1 = Ext.create('Ext.form.Panel', {
		url: 'correo/sent',
		height : 452,
		width : 800,
		layout : {
			type : 'vbox',
			//align : 'stretch',
			padding : 5
		},
		renderTo : 'new_correo',
		defaults : {
			//flex : 1
		}, // auto stretch
		items : [ {
			xtype : 'textfield',
			fieldLabel : 'Destinatario',
			name : 'to',
			allowBlank: false
		}, {
			xtype : 'textfield',
			fieldLabel : 'Asunto',
			name : 'subject',
			allowBlank: false
		}, {
			xtype : 'textfield',
			fieldLabel : 'body',
			name : 'body'
		}],
		buttons: [{
            text: 'Ingresar',
            handler: function() {
            	var sessionMask = new Ext.LoadMask(Ext.get('new_correo')
            	        , {
            	            msg:"<b>Enviando correo...</b> Espere por favor...", 
            	            removeMask:true
            	        });
                    	if(panel1.getForm().isValid() ) {
                            sessionMask.show();
                            panel1.getForm().submit({
                                success: function(form, request) {
                                    var ob =Ext.decode(request.response.responseText);
                                    if(!ob.success) {
                                        sessionMask.hide();
                                        System.MessageBox.error("<b>Error al ingresar al sistema, verifique datos.");
                                    } else {
                                    	sessionMask.hide();
                                    	System.MessageBox.info("<b>Mensaje enviado con exito</b>");
                                    }
                                    Correo.newWindow.panel.getForm().reset();
                                },
                                failure: function(form, request) {
                                    sessionMask.hide();
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
		closeAction : 'hide',
		modal : true,
		layout : 'fit',
		items : [ panel1 ]
	});
});