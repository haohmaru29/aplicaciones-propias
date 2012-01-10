Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.toolbar.Paging'
]);

Ext.onReady(function() {
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
        split : true,
        frame: false,
        border: true,
        height: 400,
        //renderTo: 'correos',
        stateId: 'stateGrid',
        columns: [{
                text     : 'Number',
                width     : 100,
                sortable : true,
                dataIndex: 'messageNumber',
                type : 'string'
            }, {
                text     : 'Fecha',
                flex     : 80,
                sortable : false,
                type : 'date',
                dataIndex: 'messageDate'
            },{
                text     : 'Remitente',
                width     : 100,
                sortable : false,
                dataIndex: 'from',
                type : 'string'
            }, {
                text     : 'Asunto',
                flex     : 300,
                sortable : false,
                dataIndex: 'subject',
                type : 'string'
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
            /*items:[
                '-', {
                text: 'botton'
            }]*/
        }),
        title: 'Mis Correos',
        region: 'north',
        viewConfig: {
            stripeRows: true,
            listeners: {
                itemclick: function(view,rec,item,index,eventObj) {
                	//alert(rec.get('body'));
                	Ext.getCmp('detailPanel').update(rec.get('body'));
                	Ext.getCmp('btn_reply').enable();
                	Ext.getCmp('btn_delete').enable();
                }
             }
        }
    });
    
    Correo.bandeja.panel = Ext.create('Ext.Panel', {
        renderTo: 'binding-example',
        frame: false,
        border: false,
        renderTo: 'correos',
        //width: 540,
        height: 600,
        layout: 'border',
        items: [
            Correo.bandeja.grid, {
                id: 'detailPanel',
                region: 'center',
                split: true,
                autoScroll: true,
                border: true,
                title: 'Detalle correo',
                bodyPadding: 7,
                bodyStyle: "background: #ffffff;",
                html: 'Seleccione correo para ver detalle.'
        }]
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
