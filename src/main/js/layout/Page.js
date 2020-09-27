import React, {useEffect, useState} from 'react';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import SockJsClient from 'react-stomp';
import LobbyService from '../services/LobbyService';

const styles = {
  header: {
    marginTop: '3rem',
  },
  blueButton: {
    color: 'blue',
  },
  lobbyTables: {
    marginTop: '4rem',
  },
  joinButtonContainer: {
    display: 'flex',
    justifyContent: 'center',
  },
  joinButton: {
    width: '10rem',
  },
  usernameInput: {
    width: '10rem',
  }
};

const Page = () => {
  const [lobbies, setLobbies] = useState([]);
  const [wsClient, setWsClient] = useState(null);
  const [loggedUser, setLoggedUser] = useState(null);
  const [usernameInputRef, setUsernameInputRef] = useState(null);
  const [topics, setTopics] = useState(['/topic/lobbies']);
  const [myLobbyId, setMyLobbyId] = useState(null);
  const [showNewLobbyModal, setShowNewLobbyModal] = useState(false);
  const [lobbyNameInputRef, setLobbyNameInputRef] = useState(false);

  useEffect(() => {
  }, []);

  const createLobby = () => {
    const msg = {
      type: 'createLobby',
      userId: loggedUser.id,
      name: lobbyNameInputRef.value
    };
    wsClient.sendMessage('/app/lobbies', JSON.stringify(msg));
  };

  const handleLoginButton = async () => {
    const inputedUsername = usernameInputRef.value;
    const user = await LobbyService.createUser(inputedUsername);
    setLoggedUser(user);
    getInitialLobbies();
  };

  const handleOnMessage = (msg, topic) => {
    console.log('received message:')
    console.log(msg)
    if (topic === '/topic/lobbies') {
      switch (msg.type) {
        case 'allLobbies':
          setLobbies(msg.lobbies);
          break;
        case 'newLobby':
          addLobby(msg.lobby);
          msg.lobby.users.forEach(user => {
            if (user.id === loggedUser.id) {
              setMyLobbyId(msg.lobby.id);
            }
          });
          break;
        case 'userJoinedLobby':
          addUserToLobby(msg.user, msg.lobbyId);
          break;
        case 'userLeftLobby':
          removeUserFromLobby(msg.userId, msg.lobbyId);
          break;
        case 'deletedLobby':
          removeLobby(msg.lobbyId);
          break;
      }
    }
  };

  const removeLobby = (lobbyId) => {
    if (myLobbyId === lobbyId) {
      setMyLobbyId(null);
    }
    const newLobbies = lobbies.filter(lobby => lobby.id !== lobbyId);
    setLobbies(newLobbies);
  }

  const removeUserFromLobby = (userId, lobbyId) => {
    const newLobbies = [...lobbies];
    for (const i in newLobbies) {
      const iLobby = newLobbies[i];
      if (iLobby.id === lobbyId) {
        iLobby.users = iLobby.users.filter(user => user.id !== userId);
      }
    }
    setLobbies(newLobbies);
    if (loggedUser.id === userId) {
      setMyLobbyId(null);
    }
  }

  const addUserToLobby = (user, lobbyId) => {
    const newLobbies = [...lobbies];
    for (const i in newLobbies) {
      if (newLobbies[i].id === lobbyId) {
        newLobbies[i].users.push(user);
        break;
      }
    }
    setLobbies(newLobbies);
    if (user.id === loggedUser.id) {
      setMyLobbyId(lobbyId);
    }
  };

  const handleCreateLobby = () => {
    setShowNewLobbyModal(false);
    createLobby();
  };

  const addLobby = (lobby) => {
    const newLobbies = [...lobbies];
    newLobbies.push(lobby);
    setLobbies(newLobbies);
  };

  const getInitialLobbies = () => {
    const msg = {
      type: 'getAllLobbies',
    };
    wsClient.sendMessage('/app/lobbies', JSON.stringify(msg));
  };

  const joinLobby = (lobby) => {
    const msg = {
      type: 'joinLobby',
      lobbyId: lobby.id,
      userId: loggedUser.id,
    };
    wsClient.sendMessage('/app/lobbies', JSON.stringify(msg));
  }

  const handleLoginSubmit = (event) => {
    event.preventDefault();
    handleLoginButton();
  }

  const leaveLobby = () => {
    const msg = {
      type: 'leaveLobby',
      lobbyId: myLobbyId,
      userId: loggedUser.id,
    }
    wsClient.sendMessage('/app/lobbies', JSON.stringify(msg));
  }

  const deleteLobby = () => {
    const msg = {
      type: 'deleteLobby',
      lobbyId: myLobbyId,
    }
    wsClient.sendMessage('/app/lobbies', JSON.stringify(msg));
  }

  const iAmHost = () => {
    const lobby = getMyLobby();
    if (!lobby) return false;
    return lobby.host.id === loggedUser.id;
  }

  const getMyLobby = () => {
    if (!myLobbyId) return null;
    return lobbies.filter(lobby => lobby.id === myLobbyId)[0];
  }

  return (
    <Container>
      <SockJsClient url='/ws/'
                    topics={topics}
                    onMessage={handleOnMessage}
                    ref={(client) => {
                      setWsClient(client);
                    }}/>
      <Modal show={showNewLobbyModal} onHide={() => setShowNewLobbyModal(false)}>
        <Modal.Body>
          <Form onSubmit={(event) => {event.preventDefault(); handleCreateLobby()}}>
            <Form.Group>
              <Form.Label>Lobby name: </Form.Label>
              <Form.Control type="text" ref={(ref) => setLobbyNameInputRef(ref)} style={styles.usernameInput}/>
            </Form.Group>
          </Form>
          <Button onClick={handleCreateLobby}>Create</Button>
        </Modal.Body>
      </Modal>
      <Row>
        <Col>
          <Row style={styles.header}>
            <Col>
              <h1>Pato</h1>
            </Col>
            <Col>
              {!!loggedUser && !myLobbyId ?
                <Button onClick={() => setShowNewLobbyModal(true)}>Create Lobby</Button> : null
              }
              {
                !!myLobbyId ?
                  iAmHost() ? <Button onClick={deleteLobby}>Delete Lobby</Button> : <Button onClick={leaveLobby}>Leave Lobby</Button> : null
              }
            </Col>
          </Row>
          <Row>
            <Col>
              {!!loggedUser && !myLobbyId ?
                <Table striped bordered hover style={styles.lobbyTables}>
                  <thead>
                  <tr>
                    <th>Lobby</th>
                    <th>Jugadores</th>
                    <th></th>
                  </tr>
                  </thead>
                  <tbody>
                  {lobbies.map((lobby, i) => {
                    console.log(lobbies)
                    return (
                      <tr key={i}>
                        <td>{lobby.name}</td>
                        <td>{lobby.users.length}</td>
                        <td style={styles.joinButtonContainer}>
                          <Button style={styles.joinButton} onClick={() => joinLobby(lobby)}>Join</Button>
                        </td>
                      </tr>
                    )
                  })}
                  </tbody>
                </Table>
                : null
              }
              {
                !loggedUser ?
                  <Container>
                    <Form onSubmit={(event) => handleLoginSubmit(event)}>
                      <Form.Group>
                        <Form.Label>Enter username: </Form.Label>
                        <Form.Control type="text" ref={(ref) => setUsernameInputRef(ref)} style={styles.usernameInput}/>
                      </Form.Group>
                    </Form>
                    <Button onClick={handleLoginButton}>Login</Button>
                  </Container> : null
              }
              {
                !!myLobbyId ?
                  lobbies.map((iLobby, i) => {
                    if (iLobby.id === myLobbyId) {
                      return (
                        <Table key={i} striped bordered hover style={styles.lobbyTables}>
                          <thead>
                          <tr>
                            <th>Players</th>
                            <th>Ready</th>
                          </tr>
                          </thead>
                          <tbody>
                          {iLobby.users.map((user, i) => {
                            return (
                              <tr key={i}>
                                <td>{user.name}</td>
                                <td>Yes(?</td>
                              </tr>
                            )
                          })}
                          </tbody>
                        </Table>
                      )
                    }
                  }) : null
              }
            </Col>
          </Row>
        </Col>
      </Row>
    </Container>
  )
};

export default Page;
