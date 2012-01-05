Ext.require([
    'Ext.data.*',
    'Ext.grid.*',
    'Ext.tree.*'
]);
Ext.onReady(function() {

	var element = '';
	
	Correo.tree.store = Ext.create('Ext.data.TreeStore', {
		autoLoad: false,
        proxy: {
            type: 'ajax',
            url: 'correo/cuentas'
        },
        root: {
        	text: 'MIS CUENTAS',
        	iconCls: 'icon-email-open',
            id: 'text',
            root: 'data',
            expanded: true
        }
    });

	Correo.tree.render = Ext.create('Ext.tree.Panel', {
        id: 'tree',
        title: 'Mis Cuentas',
        store: Correo.tree.store,
        height: 600,
        renderTo: 'mis_bandejas',
        tbar: Ext.create('Ext.Toolbar',{
        	items:[{
        		iconCls: 'icon-refresh',
        		tooltip: 'Actualizar cuentas',
        		handler: function() {
        			Correo.tree.store.load();
        		}
        	}, {
        		iconCls: 'icon-add',
        		tooltip: 'Agregar nueva cuenta correo',
        		handler: function() {
        			Correo.newAccount.win.show();
        		}
        	},{
        		iconCls: 'icon-delete',
        		tooltip: 'Eliminar cuenta',
        		handler: function() {
        			if(element == '') {
        				System.MessageBox.info("Debe seleccionar cuenta a eliminar");
        				return false;
        			} 
        			Ext.MessageBox.confirm('Confirmar', 'Esta seguro de eliminar cuenta?', function(btn, text) {
        				if(btn=='yes') {
    					 	Ext.Ajax.request({
	    				         url : 'admin/delete',
				                  method: 'POST',
				                  params :{id: element, mngr: 'UsuarioServidorCorreo'},
				                  success: function ( result, request ) {
				                	  Correo.tree.store.load();
				                	  System.MessageBox.info("<b>Cuenta eliminada con exito</b>");
					              },
				                  failure: function ( result, request ) {
				                	  System.MessageBox.error("<b>Problemas al eliminar cuenta</b>, favor intente mas tarde.");
					              }
    				       });
        				}
        			});
        		}
        	} ]
        }),
        listeners: {
            itemclick: function(view,rec,item,index,eventObj) {   
            	element = rec.get('id');
            },
        	itemdblclick : function(view,rec,item,index,eventObj) {   
        		Correo.bandeja.store.load({
            		params: {idCuenta:rec.get('id') }
            	}); 
			}	
        }
    });
});