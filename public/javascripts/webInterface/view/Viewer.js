Ext.define('Archer.view.Viewer', {

	extend: 'Ext.tab.Panel',
	alias: 'widget.viewer',

	requires: ['Archer.view.library.Show'],

	activeItem: 0,
	margins: '8 8 8 3',
	border: false,

	cls: 'preview',

	/*tabBar: {
		items: [{
			xtype: 'tbfill'
		}, {
			xtype: 'button',
			text: 'Test Button',
			margins: '3 5 0 5'
		}]
	},*/

	initComponent: function() {
		this.items = [
			{
				xtype: 'libraryshow',
				title: 'Dashboard'
			},
			{
				xtype: 'libraryshow',
				title: 'Random Collection',
				closable: true
			},
			{
				xtype: 'geocachepreview',
				title: 'GC123456 Hallo Welt',
				closable: true
			}];
		this.callParent(arguments);
		this.setActiveTab(1);
	}

});