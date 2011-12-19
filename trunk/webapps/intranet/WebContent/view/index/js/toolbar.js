Ext.onReady(function(){
    Ext.create('Ext.toolbar.Toolbar', {
        renderTo: 'toolbar',
        frame: false,
        width   : '100%',
        items: [{
            text: 'Cerrar Session',
            iconCls: 'icon-close-session',
            tooltip: 'Finalizar sesion',
            handler: function() {
                System.closeSession();
            }
        }, {
            text: 'Mis Datos',
            iconCls: 'icon-my-user',
            tooltip: 'Visualizar mis datos',
            handler: function() {
                Usuario.win.show();
            }
        }, {
            text: 'Repositorios',
            iconCls: 'icon-app',
            tooltip: 'Ver mis repositorios',
            handler: function() {
                Repositorio.win.show();
                Repositorio.store.load();
            }
        },'|', {
        	text: 'Administrar',
            iconCls: 'icon-bullet',
            menu: {
                xtype: 'menu',
                plain: true,
                items: {
                    xtype: 'buttongroup',
                    title: 'User options',
                    columns: 2,
                    defaults: {
                        xtype: 'button',
                        scale: 'large',
                        iconAlign: 'left'
                    },
                    items: [{
                        colspan: 2,
                        text: 'Usuarios',
                        scale: 'small',
                        width: 130
                    },{
                        colspan: 2,
                        text: 'Repositorios',
                        scale: 'small',
                        width: 130
                    },{
                        colspan: 2,
                        text: 'Empresas',
                        scale: 'small',
                        width: 130
                    },{
                        colspan: 2,
                        text: 'Proyectos',
                        scale: 'small',
                        width: 130
                    }]
                }
            }
        },
        '->' , {
            text: 'Ayuda',
            iconCls: 'icon-help',
            tooltip: 'Ayuda del sistema',
            handler: function() {
                alert('ayuda');
            }
        }
        ]
    });
});