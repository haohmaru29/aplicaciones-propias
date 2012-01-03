Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.toolbar.Paging'
]);

Ext.onReady(function() {
    Ext.QuickTips.init();
    var pageSize = 18;    
    Ext.define('data', {
        extend: 'Ext.data.Model',
        fields: ['from', 'subject', 'messageNumber', 'messageDate', 'body']
    });
    
    Correo.bandeja.store = Ext.create('Ext.data.JsonStore', {
          autoLoad: false,
          pageSize: pageSize,
          model: 'data',
          remoteSort: false,
          proxy: {
              type: 'ajax',
              url: 'correo/mails',
              method: 'post',
              timeout: 550000,
              reader: {
                  type: 'json',
                  root: 'data',
                  totalProperty : 'count'
              }
          },
          idProperty: 'messageNumber',
          fields: ['from', 'subject', 'messageNumber', 'messageDate', 'body']
    });
    
    Correo.bandeja.grid = Ext.create('Ext.grid.Panel', {
        store: Correo.bandeja.store,
        stateful: true,
        frame: false,
        border: true,
        height: 500,
        renderTo: 'correos',
        stateId: 'stateGrid',
        columns: [{
                text     : 'Number',
                width     : 100,
                sortable : true,
                dataIndex: 'messageNumber'
            }, {
                text     : 'Fecha',
                flex     : 80,
                sortable : false,
                dataIndex: 'messageDate'
            },{
                text     : 'Remitente',
                width     : 100,
                sortable : false,
                dataIndex: 'from'
            }, {
                text     : 'Asunto',
                flex     : 300,
                sortable : false,
                dataIndex: 'subject'
            }
        ],
        tbar: Ext.create('Ext.Toolbar',{
        	items:[{
        		iconCls: 'icon-email-add',
        		tooltip: 'Crear nuevo email',
        		handler: function() {
        			Correo.newWindow.show();
        		}
        	}, {
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
        	}]
        }),
        bbar: Ext.create('Ext.PagingToolbar', {
            store: Correo.bandeja.store,
            displayInfo: true,
            displayMsg: 'Mostrando {0} - {1} de {2}',
            emptyMsg: "No hay mensajes para mostrar",
            items:[
                '-', {
                text: 'botton'
            }]
        }),
        title: 'Mis Correos',
        viewConfig: {
            stripeRows: true,
            listeners: {
                itemclick: function(view,rec,item,index,eventObj) {
                	alert(rec.get('body'));
                	Ext.getCmp('btn_reply').enable();
                	Ext.getCmp('btn_delete').enable();
                }
             }
        }
    });
    /*
    var task = {
	   run: function() {
		   Correo.bandeja.store.load();
	   },
	   interval: 600000
	};
	 
	var runner = new Ext.util.TaskRunner();
	runner.start(task);
	*/
});
