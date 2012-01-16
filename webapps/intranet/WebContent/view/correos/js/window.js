Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.toolbar.Paging'
]);

Ext.onReady(function() {
    Ext.QuickTips.init();
    //var pageSize = 20;    
    Ext.define('data', {
        extend: 'Ext.data.Model',
        fields: ['from', 'subject', 'messageNumber', 'messageDate', 'body']
    });
    
    Home.store = Ext.create('Ext.data.JsonStore', {
          autoLoad: true,
          //pageSize: pageSize,
          model: 'data',
          remoteSort: false,
          proxy: {
              type: 'ajax',
              url: 'mail/gmail',
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
    
    Home.grid = Ext.create('Ext.grid.Panel', {
        store: Home.store,
        stateful: true,
        frame: false,
        border: false,
        stateId: 'stateGrid',
        columns: [{
                text     : 'Number',
                width     : 100,
                sortable : true,
                dataIndex: 'messageNumber'
            }, {
                text     : 'Fecha',
                flex     : 1,
                sortable : false,
                dataIndex: 'messageDate'
            },{
                text     : 'Desde',
                width     : 100,
                sortable : false,
                dataIndex: 'from'
            }, {
                text     : 'subject',
                flex     : 1,
                sortable : false,
                dataIndex: 'subject'
            }
        ],
        title: 'Resultados',
        viewConfig: {
            stripeRows: true
        }
    });
    
    var displayPanel = Ext.create('Ext.Panel', {
          height: 452,
          width: 800,
          layout       : {
              type: 'hbox',
              align: 'stretch',
              padding: 5
          },
          renderTo: 'bandeja',
          defaults     : { flex : 1 }, //auto stretch
          items        : [
              Home.grid
          ]
    });
});
