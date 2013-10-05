Ext.define('Archer.view.list.Show', {

	extend: 'Ext.panel.Panel',
	alias: 'widget.listshow',

	requires: [
		'Archer.view.geocache.Grid',
		'Archer.view.geocache.Preview'
	],

	closable: false,
	border: false,

	layout: 'border',

	initComponent: function() {
		Ext.apply(this, {
			items: [{
				xtype: 'geocachegrid',
				region: 'west',
				width: 400
			},{
				xtype: 'geocachepreview',
				cls: 'geocachepreview',
				region: 'center'
			}]
		});
		this.callParent(arguments);
	}

});