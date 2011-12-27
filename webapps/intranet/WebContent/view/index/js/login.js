Ext.onReady(function(){
   
    var loginForm = Ext.create('Ext.form.Panel',{
        url: 'user/login',
        title: ' Ingreso Intranet ',
        renderTo: 'loginDiv',
        frame: true,
        width: 350,
        items: [{
            xtype: 'textfield',
            fieldLabel: 'Usuario',
            name: 'nombre',
            allowBlank: false
        },{
            xtype: 'textfield',
            inputType: 'password',
            fieldLabel: 'Clave',
            allowBlank: false,
            name: 'clave'
        }],
        buttons: [{
            text: 'Ingresar',
            handler: function() {
                fnLoginForm(loginForm);
            }
        },{
            text: 'Limpiar',
            handler: function() {
                loginForm.getForm().reset();
            }
        }]
    });
});
	 
function fnLoginForm(theForm) {
    var sessionMask = new Ext.LoadMask(Ext.getBody()
        , {
            msg:"<b>Iniciando Sesi&oacute;n...</b> Espere por favor...", 
            removeMask:true
        });
    if(theForm.getForm().isValid() ) {
        sessionMask.show();
        theForm.getForm().submit({
            success: function(form, request) {
                var ob =Ext.decode(request.response.responseText);
                if(!ob.success) {
                    sessionMask.hide();
                    System.MessageBox.error("<b>Error al ingresar al sistema, verifique datos.");
                } else {
                    location.href = 'index.jsp'; 
                }
            },
            failure: function(form, request) {
                sessionMask.hide();
                System.MessageBox.error("<b>Se ha producido un error. " );
            }
        });
    } else {
        System.MessageBox.info("<b>Ingrese todos los campos requeridos.</b>");
    }
    
} 