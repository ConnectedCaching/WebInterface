Ext.application({

	name: 'Archer',

	appFolder: '/assets/javascripts/webInterface/',

	// All the paths for custom classes
	paths: {
		'Ext.ux': '/assets/javascripts/webInterface/plugins'
	},

	// Define all the controllers that should initialize at boot up of your application
	//controllers: [
	//	'Articles',
	//	'Feeds'
	//],

	autoCreateViewport: false,

	launch: function() {
		Ext.create('Archer.view.Viewport', {
			renderTo: Ext.Element.get('app'),
			width: '100%',
			height: '100%'
		});
	}

});
