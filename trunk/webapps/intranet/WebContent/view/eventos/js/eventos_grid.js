Ext.onReady(function(){
    /*
	Ext.define('eventos_usuario', {
        extend: 'Ext.data.Model',
        fields: ['titulo', 'fecha_inicio', 'idusuario_evento']
    });
    */
    Evento.grid.store = Ext.create('Ext.data.JsonStore', {
          autoLoad: true,
          //model: 'eventos_usuario',
          remoteSort: false,
          proxy: {
              type: 'ajax',
              url: 'eventos/usuario',
              method: 'post',
              timeout: 550000,
              reader: {
                  type: 'json',
                  root: 'data',
                  totalProperty : 'count'
              }
          },
          idProperty: 'idusuario_evento',
          fields: [ 'titulo', 'fechaInicio', 'idevento' ]
    });
    
    Evento.grid.render = Ext.create('Ext.grid.Panel', {
        store: Evento.grid.store,
        stateful: true,
        split : true,
        frame: false,
        border: true,
        height: 600,
        renderTo: 'mis_eventos',
        stateId: 'stateGrid',
        columns: [{
                text     : 'Id',
                width     : 30,
                sortable : true,
                dataIndex: 'idevento'
            }, {
                text     : 'Fecha inicio',
                flex     : 80,
                sortable : false,
                type : 'date',
                dataIndex: 'fechaInicio'
            },/*{
                text     : 'Titulo',
                width     : 100,
                sortable : false,
                dataIndex: 'titulo',
                type : 'string'
            }, */{
                xtype: 'actioncolumn',
                width: 50,
                items: [{
                	icon: 'library/system/icons/application_form_edit.png',
                    tooltip: 'Editar evento',
                    handler: function(grid, rowIndex, colIndex) {
                    	var rec = Evento.grid.store.getAt(rowIndex);
                    	alert("evento " + rec.get('idevento'));
                    }
                }, {
                	icon: 'library/system/icons/delete.png',
                    tooltip: 'Eliminar evento',
                    handler: function(grid, rowIndex, colIndex) {
                        Ext.MessageBox.confirm(System.name, 'Esta seguro de eliminar el evento?', function(btn) {
                        		if(btn == 'yes') {
                        			var rec = Evento.grid.store.getAt(rowIndex);
                                	var element = rec.get('idevento');
                                	Ext.Ajax.request({
	       	    				         url : 'admin/delete',
	       				                  method: 'POST',
	       				                  params :{id: element, mngr: 'Evento'},
	       				                  success: function ( result, request ) {
	       				                	  Evento.grid.store.load();
	       				                	  System.MessageBox.info("<b>Evento eliminado con exito</b>");
	       					              },
	       				                  failure: function ( result, request ) {
	       				                	  System.MessageBox.error("<b>Problemas al eliminar evento</b>, favor intente mas tarde.");
	       					              }
	           				       });
                                	
                                	
                        		} 
                        });
                    }
                }]
            }
        ],
        items :[{
        	xtype: 'panel',
            border: false,
            layout: {
                type: 'vbox',
                align: 'stretch'
            }
        }],
        tbar: Ext.create('Ext.Toolbar',{
        	items:[{
        		iconCls: 'icon-add',
        		tooltip: 'Nuevo evento',
        		handler: function() {
        			Evento.newEvento.window.show();
        		}
        	}/*, {
        		iconCls: 'icon-email-edit',
        		tooltip: 'Responder email',
        		id: 'btn_reply',
        		disabled: true,
        		handler: function() {
        			//Correo.newWindow.show();
        		}
        	},{
        		iconCls: 'icon-email-delete',
        		tooltip: 'Eliminar email',
        		id: 'btn_delete',
        		disabled: true,
        		handler: function() {
        			//Correo.newWindow.show();
        		}
        	}*/]
        }),/*
        bbar: Ext.create('Ext.PagingToolbar', {
            store: Correo.bandeja.store,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No hay mensajes para mostrar",
            items:[
                '-', {
                text: 'botton'
            }]
        }),*/
        title: 'Proximos eventos',
        //region: 'north',
        viewConfig: {
            stripeRows: true/*,
            listeners: {
                itemclick: function(view,rec,item,index,eventObj) {
                	//alert(rec.get('body'));
                	Ext.getCmp('detailPanel').update(rec.get('body'));
                	Ext.getCmp('btn_reply').enable();
                	Ext.getCmp('btn_delete').enable();
                }
             }*/
        }
    });
	
});