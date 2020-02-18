import React from 'react';
import Router from './components/routing/router';
import { HashRouter } from 'react-router-dom' 
import "./styles.css";

class App extends React.Component {
    constructor(props) {
      super(props);
    }
  
    render() {
      return (
        <div>
            <HashRouter>
              <Router/>
            </HashRouter>
        </div>
      );
    }
}

export default App