Ext.require([
    'Ext.window.*'
]);

Ext.onReady(function() {
    Usuario.win= new Ext.Window({
        title: 'Visualizar Informaci&oacute;n Propia...',
        closable:true,
        frame: false,
        width:500,
        height:400,
        closeAction:'hide',
        //plain:true,
        modal:true,
        layout: 'fit',
        items: [{
            region: 'center',
            xtype: 'panel',
            items: [{
                border: false,
                layout: 'fit',
                height: 160,
                title: 'Datos Personales',
                contentEl: 'user_datos_personales'
            },{
                border: true,
                layout: 'fit',
                height: '100%',
                title: 'Datos Empresa',
                contentEl: 'user_datos_empresariales'
            }]
        }]
        
    });
    
    var perfil =new Ext.ux.Element('user/session', function(result, request){
        var o =Ext.decode(result.responseText);
        if( o.success ) {
            document.getElementById('session_nombreUsuario').innerHTML = o.data.nombre;
            document.getElementById('session_mailUsuario').innerHTML = o.data.correo;
            document.getElementById('session_nombreEmpresa').innerHTML = o.data.empresa.empresa;
            document.getElementById('session_usuarioCargo').innerHTML = o.data.cargo.nombreCargo;
            
        }
    }, null );
     
    var pool= new Ext.ux.ajax.pool();
    pool.appendObject( perfil );
    //pool.appendObject( Correo.tree.render );
    pool.init();
});
