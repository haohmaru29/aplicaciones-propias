Ext.onReady(function(){

    Evento.grid.store = Ext.create('Ext.data.JsonStore', {
          autoLoad: true,
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
          idProperty: 'id',
          fields: [ 'title', 'start', 'id' ]
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
                dataIndex: 'id'
            }, {
                text     : 'Fecha inicio',
                flex     : 80,
                sortable : false,
                type : 'date',
                dataIndex: 'start'
            }, {
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
        	}]
        }),
        title: 'Proximos eventos',
        viewConfig: {
            stripeRows: true
        }
    });
	
});