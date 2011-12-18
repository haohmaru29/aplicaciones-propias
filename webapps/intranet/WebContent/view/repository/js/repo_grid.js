Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.toolbar.Paging'
]);

Ext.onReady(function() {
    var pageSize = 20;    
    Ext.define('repositorio', {
        extend: 'Ext.data.Model',
        fields: ['idrepositorio', 'nombreRepositorio' ]
    });
    
    Repositorio.store = Ext.create('Ext.data.JsonStore', {
          autoLoad: false,
          pageSize: pageSize,
          model: 'repositorio',
          remoteSort: false,
          proxy: {
              type: 'ajax',
              url: 'repositorio/my',
              method: 'post',
              timeout: 550000,
              reader: {
                  type: 'json',
                  root: 'data',
                  totalProperty : 'count'
              }
          },
          idProperty: 'idrepositorio',
          fields: ['idrepositorio', 'nombreRepositorio' ]
    });
    
    Repositorio.grid = Ext.create('Ext.grid.Panel', {
        store: Repositorio.store,
        stateful: true,
        frame: false,
        border: true,
        height: 500,
        width: 500,
        stateId: 'stateGrid',
        //renderTo: 'grid_repositorios',
        columns: [{
                text     : 'ID',
                width     : 100,
                sortable : true,
                dataIndex: 'idrepositorio'
            }, {
                text     : 'URL REPOSITORIO',
                flex     : 1,
                sortable : false,
                dataIndex: 'nombreRepositorio'
            }
        ],
        //title: 'Mis Repositorios', 
        viewConfig: {
            stripeRows: true
        }
    });
});



