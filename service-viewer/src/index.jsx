import React from 'react';
import ReactDOM from 'react-dom';
import App from './App.js'
import "./styles.css";

const APP_TITLE = React.createElement(
	"title", 
	{className: 'title'},
	"Netflix Movie Searcher"
);     

const root = React.createElement("div", null, APP_TITLE, <App/>);

ReactDOM.render(
	root,
	document.getElementById('root')
)