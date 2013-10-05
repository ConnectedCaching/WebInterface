Ext.define('Archer.view.Viewer', {

	extend: 'Ext.tab.Panel',
	alias: 'widget.viewer',

	requires: ['Archer.view.list.Show'],

	activeItem: 0,
	margins: '5 5 5 5',
	border: false,

	cls: 'preview',

	initComponent: function() {
		this.items = [{
			xtype: 'listshow',
			title: 'Sencha Blog'
		}];
		this.callParent(arguments);
	}

});