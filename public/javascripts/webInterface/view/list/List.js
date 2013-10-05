Ext.define('Archer.view.list.List', {

	extend: 'Ext.panel.Panel',
	alias: 'widget.list',

	requires: ['Ext.toolbar.Toolbar'],

	title: 'Lists',
	collapsible: true,
	animCollapse: false,
	margins: '5 0 5 5',
	layout: 'fit',
	split: true,

	initComponent: function() {

		Ext.apply(this, {
			items: [{
				xtype: 'dataview',
				trackOver: true,
				store: this.store,
				cls: 'feed-list',
				itemSelector: '.feed-list-item',
				overItemCls: 'feed-list-item-hover',
				tpl: '<tpl for="."><div class="feed-list-item">{name}</div></tpl>',
				listeners: {
					selectionchange: this.onSelectionChange,
					scope: this
				}
			}],

			dockedItems: [{
				xtype: 'toolbar',
				items: [{
					iconCls: 'feed-add',
					text: 'Add Feed',
					action: 'add'
				}, {
					iconCls: 'feed-remove',
					text: 'Remove Feed',
					disabled: true,
					action: 'remove'
				}]
			}]
		});
		this.callParent(arguments);

	},

	onSelectionChange: function(selmodel, selection) {
		var selected = selection[0],
			button = this.down('button[action=remove]');
		if (selected) {
			button.enable();
		}
		else {
			button.disable();
		}
	}
});
