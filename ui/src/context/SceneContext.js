import React, { Component, useContext } from 'react'

const SceneContext = React.createContext()

class SceneProvider extends Component {
  state = {
    scene: null
  }

  componentDidMount() {
    const scene = localStorage.getItem('scene');
    this.setState({ scene })
  }


  getScene = () => {
    return JSON.parse(localStorage.getItem('scene'))
  }


  setScene= scene=>{
    localStorage.setItem('scene', JSON.stringify(scene))
  }


  render() {
    const { children } = this.props
    const { scene } = this.state
    const { getScene, setScene } = this

    return (
      <SceneContext.Provider value={{ scene,getScene, setScene }}>
        {children}
      </SceneContext.Provider>
    )
  }
}

export default SceneContext

export function useScene() {
  return useContext(SceneContext)
}

export { SceneProvider }