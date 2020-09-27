import React, {Component} from 'react'
import ReactDOM from 'react-dom'
import {BrowserRouter, Route, Switch} from 'react-router-dom'

import Page from './layout/Page'

class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Route path='/' component={Page}/>
        </Switch>
      </BrowserRouter>
    )
  }
}

ReactDOM.render(<App/>, document.getElementById('root'));
