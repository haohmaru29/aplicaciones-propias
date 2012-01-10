Ext.onReady(function(){
    Ext.define('eventos_usuario', {
        extend: 'Ext.data.Model',
        fields: ['titulo', 'fecha_inicio', 'idusuario_evento']
    });
    
    Evento.grid.store = Ext.create('Ext.data.JsonStore', {
          autoLoad: true,
          model: 'eventos_usuario',
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
          fields: [ 'titulo', 'fecha_inicio', 'idusuario_evento' ]
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
                width     : 100,
                sortable : true,
                dataIndex: 'idusuario_evento'
            }, {
                text     : 'Fecha inicio',
                flex     : 80,
                sortable : false,
                type : 'date',
                dataIndex: 'fecha_inicio'
            },{
                text     : 'Titulo',
                width     : 100,
                sortable : false,
                dataIndex: 'titulo',
                type : 'string'
            }, {
                xtype: 'actioncolumn',
                width: 50,
                items: [{
                    iconCls   : 'icon-edit',
                    tooltip: 'Editar evento',
                    handler: function(grid, rowIndex, colIndex) {
                        /*
                          	var rec = store.getAt(rowIndex);
                        	alert("Sell " + rec.get('company'));
                        */
                    }
                }, {
                    iconCls   : 'icon-delete',
                    tooltip: 'Eliminar evento',
                    handler: function(grid, rowIndex, colIndex) {
                        Ext.MessageBox.confirm('Esta seguro de eliminar el evento?', function(btn) {
                        		if(btn == 'yes') {
                        			
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