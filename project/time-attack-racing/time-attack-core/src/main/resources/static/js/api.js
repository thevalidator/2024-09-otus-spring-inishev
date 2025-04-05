var spec = {
  "openapi": "3.1.0",
  "x-stoplight": {
    "id": "5dom0uxp5a2nv"
  },
  "info": {
    "title": "Time Attack Racing API",
    "version": "1.0",
    "x-logo": {
      "url": "../img/tar-api-logo.png"
    },
    "contact": {
      "url": "http://localhost:8080",
      "name": "",
      "email": ""
    },
    "summary": "Сменить роль пользователю",
    "description": "",
    "license": {
      "name": "GPLv3",
      "url": "https://www.gnu.org/licenses/gpl-3.0.html"
    },
    "termsOfService": ""
  },
  "servers": [
    {
      "url": "http://localhost",
      "description": ""
    }
  ],
  "paths": {
    "/api/v1/sessions/{session_id}/laps": {
      "parameters": [
        {
          "schema": {
            "type": "string"
          },
          "name": "session_id",
          "in": "path",
          "required": true,
          "description": "Идентификатор сессии"
        }
      ],
      "post": {
        "summary": "Загрузить круги",
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/LapsSaveResult"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "successful": 120,
                      "failed": 0,
                      "total": 120
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "post-api-v1-sessions-session_id-laps",
        "x-stoplight": {
          "id": "xnuspsx2ppaq7"
        },
        "requestBody": {
          "content": {
            "multipart/form-data": {
              "schema": {
                "type": "object"
              }
            }
          }
        },
        "parameters": [
          {
            "schema": {
              "type": "string",
              "enum": [
                "CSV"
              ]
            },
            "in": "query",
            "name": "fileType",
            "required": true,
            "description": "Поддерживаемый тип файла с данными телеметрии "
          }
        ],
        "tags": [
          "session"
        ]
      },
      "get": {
        "summary": "Получить все круги сессии",
        "tags": [
          "session"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/SessionLaps"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "data": [
                        {
                          "crew": {
                            "id": "497f6eca-6276-4993-bfeb-53cbbbba6f08",
                            "pilot_name": "Turbo John",
                            "vehicle_name": "Toyota GR86",
                            "racing_number": 56
                          },
                          "lap_times": [
                            "01:53.575",
                            "01:49.491"
                          ]
                        }
                      ]
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "get-api-v1-sessions-session_id-laps",
        "x-stoplight": {
          "id": "qho600zbebfp8"
        },
        "parameters": [
          {
            "schema": {
              "type": "integer",
              "minimum": 1,
              "maximum": 99
            },
            "in": "query",
            "name": "racingNumber",
            "description": "Выводит результат для конкретного экипажа"
          }
        ]
      }
    },
    "/api/v1/events/{event_id}/sessions": {
      "parameters": [
        {
          "schema": {
            "type": "integer",
            "format": "int64",
            "exclusiveMinimum": false,
            "minimum": 1
          },
          "name": "event_id",
          "in": "path",
          "required": true,
          "description": "Идентификатор соревнования"
        }
      ],
      "get": {
        "description": "",
        "summary": "Получить все сессии соревнования",
        "operationId": "post-api-events-event_id-all-sessions",
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/EventSession",
                    "x-stoplight": {
                      "id": "7nvvasnjgimeo"
                    }
                  }
                },
                "examples": {
                  "Example 1": {
                    "value": [
                      {
                        "session_id": 138,
                        "ordinal_number": 1,
                        "session_type": {
                          "id": 1,
                          "type": "PRACTICE"
                        },
                        "session_name": "First practice",
                        "laps_limit": 999
                      },
                      {
                        "session_id": 141,
                        "ordinal_number": 2,
                        "session_type": {
                          "id": 1,
                          "type": "COMPETITION"
                        },
                        "session_name": "Race",
                        "laps_limit": 1
                      }
                    ]
                  }
                }
              }
            }
          }
        },
        "x-stoplight": {
          "id": "jdmajnx6cq1ar"
        },
        "tags": [
          "session"
        ]
      },
      "post": {
        "summary": "Создать сессию",
        "responses": {
          "201": {
            "description": "Created",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/EventSession"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "session_id": 138,
                      "ordinal_number": 1,
                      "session_type": {
                        "id": 1,
                        "type": "PRACTICE"
                      },
                      "session_name": "First practice",
                      "laps_limit": 999
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "post-api-events-event_id-session",
        "x-stoplight": {
          "id": "s78b39p30a63c"
        },
        "description": "Создание практической или зачетной сессии соревнования.",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/CreateEventSessionRequest"
              },
              "examples": {
                "Example 1": {
                  "value": {
                    "session_name": "Practice 1",
                    "type_id": 1,
                    "laps_limit": 999
                  }
                }
              }
            }
          }
        },
        "tags": [
          "session"
        ]
      }
    },
    "/api/v1/sessions/types": {
      "get": {
        "summary": "Получить список доступных типов сессий",
        "tags": [
          "session"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/SessionType",
                    "x-stoplight": {
                      "id": "lwt0unrqzbat7"
                    }
                  }
                },
                "examples": {
                  "Example 1": {
                    "value": [
                      {
                        "id": 1,
                        "type": "PRACTICE"
                      },
                      {
                        "id": 1,
                        "type": "COMPETITION"
                      }
                    ]
                  }
                }
              }
            }
          }
        },
        "operationId": "get-api-v1-session-types",
        "x-stoplight": {
          "id": "0goubftfaq41h"
        }
      },
      "parameters": []
    },
    "/api/v1/tracks": {
      "post": {
        "summary": "Создать трек",
        "tags": [
          "track"
        ],
        "responses": {
          "201": {
            "description": "Created",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/Track"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "id": 12,
                      "track_name": "Moscow Raceway"
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "post-api-v1-tracks",
        "x-stoplight": {
          "id": "n34qo9efvae7v"
        },
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/CreateTrackRequest"
              },
              "examples": {
                "Example 1": {
                  "value": {
                    "track_name": "Moscow Raceway"
                  }
                }
              }
            }
          }
        }
      },
      "get": {
        "summary": "Получить все доступные треки",
        "tags": [
          "track"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/Track",
                    "x-stoplight": {
                      "id": "s3sf9vjs3f74r"
                    }
                  }
                },
                "examples": {
                  "Example 1": {
                    "value": [
                      {
                        "id": 1,
                        "track_name": "ADM Raceway"
                      },
                      {
                        "id": 2,
                        "track_name": "Moscow Raceway"
                      }
                    ]
                  }
                }
              }
            }
          }
        },
        "operationId": "get-api-v1-tracks",
        "x-stoplight": {
          "id": "ridbb9nh5jxnt"
        }
      }
    },
    "/tracks/{track_id}": {
      "parameters": [
        {
          "schema": {
            "type": "string"
          },
          "name": "track_id",
          "in": "path",
          "required": true,
          "description": "Идентификатор гоночного трека"
        }
      ],
      "get": {
        "summary": "Получить трек",
        "tags": [
          "track"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/Track"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "id": 12,
                      "track_name": "Moscow Raceway"
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "get-tracks-track_id",
        "x-stoplight": {
          "id": "mcw0eibr9yqyx"
        }
      }
    },
    "/api/v1/events/{event_id}/crews": {
      "parameters": [
        {
          "schema": {
            "type": "string"
          },
          "name": "event_id",
          "in": "path",
          "required": true,
          "description": "Идентификатор соревнования"
        }
      ],
      "post": {
        "summary": "Подать заявку на участие",
        "responses": {
          "201": {
            "description": "Created",
            "content": {}
          }
        },
        "operationId": "post-api-v1-events-event_id-registrations",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/UserEventRegistrationRequest"
              },
              "examples": {
                "Example 1": {
                  "value": {
                    "user_id": "a169451c-8525-4352-b8ca-070dd449a1a5",
                    "vehicle_id": 56,
                    "catagory_id": 3,
                    "race_number": 17
                  }
                }
              }
            }
          }
        },
        "x-stoplight": {
          "id": "iflz7omqnyyie"
        },
        "description": "Подается пользователем для регистрации на соревнование",
        "tags": [
          "event"
        ]
      },
      "get": {
        "summary": "Получить список зарегистрированных участников",
        "tags": [
          "event"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/GroupedCrews"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "data": [
                        {
                          "category": {
                            "id": 2,
                            "name": "STREET 120",
                            "max_power": 120,
                            "wheel_drive_type": "RWD"
                          },
                          "crews": [
                            {
                              "id": "21",
                              "pilot_name": "Turbo John",
                              "vehicle_name": "Toyota GR86",
                              "starting_number": 33
                            }
                          ]
                        }
                      ]
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "get-api-v1-events-event_id-crews",
        "x-stoplight": {
          "id": "8r0ov0w3p6bk7"
        },
        "description": "Список участников, группированный по категориям"
      }
    },
    "/api/v1/events": {
      "get": {
        "summary": "Получить список всех соревнований",
        "tags": [
          "event"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/EventPage"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "events": [
                        {
                          "id": 1,
                          "date": "2019-08-24",
                          "name": "Spring Race Weekend 1",
                          "track": {
                            "id": 12,
                            "track_name": "Moscow Raceway"
                          },
                          "categories": [
                            {
                              "id": 1,
                              "name": "STREET 120",
                              "max_power_limit": 120,
                              "wheel_drive_type": "RWD"
                            },
                            {
                              "id": 2,
                              "name": "STREET 120",
                              "max_power_limit": 120,
                              "wheel_drive_type": "FWD"
                            }
                          ]
                        }
                      ],
                      "pagination": {
                        "offset": 0,
                        "page_size_limit": 10,
                        "elements": 1,
                        "has_previous": false,
                        "has_next": false
                      }
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "get-api-v1-events",
        "x-stoplight": {
          "id": "p7pyfoszpqbl2"
        },
        "requestBody": {
          "content": {}
        },
        "parameters": [
          {
            "schema": {
              "type": "integer",
              "default": 0
            },
            "in": "query",
            "name": "offset",
            "description": "Количество пропущенных записей"
          },
          {
            "schema": {
              "type": "integer",
              "default": 10
            },
            "in": "query",
            "name": "size",
            "description": "Количество записей на странице"
          }
        ]
      },
      "post": {
        "summary": "Создать соревнование",
        "tags": [
          "event"
        ],
        "responses": {
          "201": {
            "description": "Created",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/Event"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "id": 1,
                      "date": "2019-08-24",
                      "name": "string",
                      "track": {
                        "id": 12,
                        "track_name": "Moscow Raceway"
                      },
                      "categories": [
                        {
                          "id": 1,
                          "name": "STREET 120",
                          "max_power_limit": 120,
                          "wheel_drive_type": "RWD"
                        },
                        {
                          "id": 2,
                          "name": "STREET 120",
                          "max_power_limit": 120,
                          "wheel_drive_type": "FWD"
                        }
                      ]
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "post-api-v1-events",
        "x-stoplight": {
          "id": "hz6l7qve8ckz1"
        },
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/CreateEventRequest"
              },
              "examples": {
                "Example 1": {
                  "value": {
                    "name": "Spring Race Weekend",
                    "date": "2019-08-24",
                    "track_id": 1,
                    "category_ids": [
                      1,
                      3,
                      5
                    ]
                  }
                }
              }
            }
          }
        }
      }
    },
    "/api/v1/events/{event_id}/results": {
      "parameters": [
        {
          "schema": {
            "type": "string"
          },
          "name": "event_id",
          "in": "path",
          "required": true,
          "description": "Идентификатор соревнования"
        }
      ],
      "get": {
        "summary": "Получить итоговые результаты соревнования",
        "tags": [
          "event"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "type": "object",
                  "required": [
                    "data"
                  ],
                  "properties": {
                    "data": {
                      "type": "array",
                      "x-stoplight": {
                        "id": "0m4x60dvzo9o1"
                      },
                      "items": {
                        "$ref": "#/components/schemas/CrewEventResult",
                        "x-stoplight": {
                          "id": "o0d5m7zveyrjf"
                        }
                      }
                    }
                  }
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "data": [
                        {
                          "crew": {
                            "id": "497f6eca-6276-4993-bfeb-53cbbbba6f08",
                            "pilot_name": "Turbo John",
                            "vehicle_name": "Toyota GR86",
                            "racing_number": 25
                          },
                          "competition_times": [
                            "01:47.155"
                          ],
                          "summary_time": "01:47.155"
                        }
                      ]
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "get-api-v1-events-event_id-results",
        "x-stoplight": {
          "id": "odopfkfs8sb9j"
        },
        "description": "Список итоговых результатов отсортированных в порядке возрастания суммарного времени"
      }
    },
    "/api/v1/events/{event_id}": {
      "get": {
        "summary": "Получить данные о соревновании",
        "tags": [
          "event"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/Event"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "id": 1,
                      "date": "2019-08-24",
                      "name": "Spring Race Weekend 1",
                      "track": {
                        "id": 12,
                        "track_name": "Moscow Raceway"
                      },
                      "categories": [
                        {
                          "id": 1,
                          "name": "STREET 120",
                          "max_power_limit": 120,
                          "wheel_drive_type": "RWD"
                        },
                        {
                          "id": 2,
                          "name": "STREET 120",
                          "max_power_limit": 120,
                          "wheel_drive_type": "FWD"
                        }
                      ]
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "get-api-v1-events-event-id",
        "x-stoplight": {
          "id": "ipuo6ynf03xvn"
        },
        "description": "Получение данных по соревновании"
      },
      "parameters": [
        {
          "schema": {
            "type": "string"
          },
          "name": "event_id",
          "in": "path",
          "required": true,
          "description": "Идентификатор соревнования"
        }
      ]
    },
    "/api/v1/categories": {
      "get": {
        "summary": "Получить список доступных категорий",
        "tags": [
          "category"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/Category",
                    "x-stoplight": {
                      "id": "9cituq3tt8mbi"
                    }
                  }
                },
                "examples": {
                  "Example 1": {
                    "value": [
                      {
                        "id": 1,
                        "name": "STREET 120",
                        "max_power_limit": 120,
                        "wheel_drive_type": "RWD"
                      },
                      {
                        "id": 2,
                        "name": "SPORT 350",
                        "max_power_limit": 350,
                        "wheel_drive_type": "RWD"
                      }
                    ]
                  }
                }
              }
            }
          }
        },
        "operationId": "get-api-v1-categories",
        "x-stoplight": {
          "id": "ihi7k69nq63cb"
        }
      }
    },
    "/api/v1/users/{user_id}": {
      "parameters": [
        {
          "schema": {
            "type": "string"
          },
          "name": "user_id",
          "in": "path",
          "required": true,
          "description": "Идентификатор существующего пользователя"
        }
      ],
      "get": {
        "summary": "Получить данные пользователя",
        "tags": [
          "user"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/User"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "id": "497f6eca-6276-4993-bfeb-53cbbbba6f08",
                      "first_name": "John",
                      "last_name": "Turbo",
                      "birth_date": "2006-08-24",
                      "email": "john-turbo@example.com"
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "get-api-v1-users-user_id",
        "x-stoplight": {
          "id": "jl1zz9ql5gvn4"
        }
      }
    },
    "/api/v1/users/{user_id}/set-role": {
      "parameters": [
        {
          "schema": {
            "type": "string"
          },
          "name": "user_id",
          "in": "path",
          "required": true,
          "description": "Идентификатор существующего пользователя"
        }
      ],
      "post": {
        "summary": "Сменить роль пользователю",
        "tags": [
          "user"
        ],
        "responses": {
          "204": {
            "description": "No Content"
          }
        },
        "operationId": "post-api-v1-users-user_id-set-role",
        "x-stoplight": {
          "id": "avskvzxnd6ndt"
        },
        "parameters": [
          {
            "schema": {
              "type": "string"
            },
            "in": "query",
            "name": "role",
            "description": "Имя назначаемой пользователю роли ",
            "required": true
          }
        ]
      }
    },
    "/api/v1/users/{user_id}/vehicles": {
      "parameters": [
        {
          "schema": {
            "type": "string"
          },
          "name": "user_id",
          "in": "path",
          "required": true,
          "description": "Идентификатор существующего пользователя"
        }
      ],
      "get": {
        "summary": "Получить автомобили пользователя",
        "tags": [
          "vehicle"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/Vehicle",
                    "x-stoplight": {
                      "id": "942zliqsqdpo2"
                    }
                  }
                },
                "examples": {
                  "Example 1": {
                    "value": [
                      {
                        "id": 1,
                        "make": "Toyota",
                        "model": "GR86",
                        "year": 2023,
                        "user_id": "a169451c-8525-4352-b8ca-070dd449a1a5"
                      },
                      {
                        "id": 2,
                        "make": "Toyota",
                        "model": "Supra",
                        "year": 2021,
                        "user_id": "a169451c-8525-4352-b8ca-070dd449a1a5"
                      }
                    ]
                  }
                }
              }
            }
          }
        },
        "operationId": "get-api-v1-users-user_id-vehicles",
        "x-stoplight": {
          "id": "wfhxo08hwcj1a"
        }
      }
    },
    "/api/v1/vehicles/{vehicle_id}": {
      "parameters": [
        {
          "schema": {
            "type": "string"
          },
          "name": "vehicle_id",
          "in": "path",
          "required": true
        }
      ],
      "get": {
        "summary": "Получить автомобиль по его идентификатору",
        "tags": [
          "vehicle"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/Vehicle"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "id": 1,
                      "make": "Toyota",
                      "model": "GR86",
                      "year": 2023,
                      "user_id": "a169451c-8525-4352-b8ca-070dd449a1a5"
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "get-api-v1-vehicles-vehicle_id",
        "x-stoplight": {
          "id": "p4dmpfb6p1sel"
        }
      }
    },
    "/api/v1/vehicles": {
      "parameters": [],
      "post": {
        "summary": "Создать автомобиль для пользователя",
        "tags": [
          "vehicle"
        ],
        "responses": {
          "201": {
            "description": "Created",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/Vehicle"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "id": 1,
                      "make": "Toyota",
                      "model": "GR86",
                      "year": 2023,
                      "user_id": "a169451c-8525-4352-b8ca-070dd449a1a5"
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "post-api-v1-profiles-user_id-vehicles",
        "x-stoplight": {
          "id": "xqww273d4ps2t"
        },
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/CreateVehicleRequest"
              },
              "examples": {
                "Example 1": {
                  "value": {
                    "user_id": "a169451c-8525-4352-b8ca-070dd449a1a5",
                    "make": "Toyota",
                    "model": "GR86",
                    "year": 2023
                  }
                }
              }
            }
          }
        },
        "parameters": [
          {
            "schema": {
              "type": "string"
            },
            "in": "query"
          }
        ]
      }
    },
    "/api/v1/auth/token/refresh": {
      "post": {
        "summary": "Обновить токены",
        "tags": [
          "auth"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/JwtSet"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "access_token": "access.token.value",
                      "refresh_token": "refresh.token.value"
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "post-api-v1-auth-token-refresh",
        "x-stoplight": {
          "id": "ltoiu513y1flx"
        },
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/RefreshTokenrequest"
              },
              "examples": {
                "Example 1": {
                  "value": {
                    "refresh_token": "refresh.token.value"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/api/v1/sign-out": {
      "get": {
        "summary": "Выйти из профиля",
        "responses": {
          "202": {
            "description": "Accepted"
          }
        },
        "operationId": "get-api-v1-logout",
        "x-stoplight": {
          "id": "q67llja7esnj5"
        },
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/RefreshTokenrequest"
              },
              "examples": {
                "Example 1": {
                  "value": {
                    "refresh_token": "refresh.token.value"
                  }
                }
              }
            }
          }
        },
        "tags": [
          "auth"
        ]
      },
      "parameters": []
    },
    "/api/v1/sign-in": {
      "post": {
        "summary": "Вход пользователя",
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/JwtSet"
                },
                "examples": {
                  "Example 1": {
                    "value": {
                      "access_token": "access.token.value",
                      "refresh_token": "refresh.token.value"
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "post-api-v1-login",
        "x-stoplight": {
          "id": "7tor9rb3pakaz"
        },
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/LoginForm"
              },
              "examples": {
                "Example 1": {
                  "value": {
                    "email": "john-turbo@example.com",
                    "password": "pa$$word"
                  }
                }
              }
            }
          }
        },
        "tags": [
          "auth"
        ]
      },
      "parameters": []
    },
    "/api/v1/sign-up": {
      "post": {
        "summary": "Регистрация пользователя",
        "responses": {
          "201": {
            "description": "Created"
          }
        },
        "operationId": "post-api-v1-register",
        "x-stoplight": {
          "id": "60vwt65ruutgy"
        },
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/UserRegistrationRequest"
              },
              "examples": {
                "Example 1": {
                  "value": {
                    "password": "pa$$word",
                    "first_name": "John",
                    "last_name": "Turbo",
                    "birth_date": "2006-08-24",
                    "email": "john-turbo@example.com"
                  }
                }
              }
            }
          }
        },
        "tags": [
          "auth"
        ]
      },
      "parameters": []
    },
    "/api/v1/sessions/{session_id}/leaderboards": {
      "parameters": [
        {
          "schema": {
            "type": "string"
          },
          "name": "session_id",
          "in": "path",
          "required": true
        }
      ],
      "get": {
        "summary": "Получить лучшие круги сессии по экипажам",
        "tags": [
          "session"
        ],
        "responses": {
          "200": {
            "description": "Лучшие круги экипажей отсортированные в порядке возрастания",
            "content": {
              "application/json": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/CrewSessionLaps",
                    "x-stoplight": {
                      "id": "l7tin3lpl6oep"
                    }
                  }
                },
                "examples": {
                  "Example 1": {
                    "value": [
                      {
                        "crew": {
                          "id": "497f6eca-6276-4993-bfeb-53cbbbba6f08",
                          "pilot_name": "Turbo John",
                          "vehicle_name": "Toyota GR86",
                          "racing_number": 56
                        },
                        "lap_times": [
                          "01:53.575"
                        ]
                      }
                    ]
                  }
                }
              }
            }
          }
        },
        "operationId": "get-api-v1-sessions-session_id-leaderboards",
        "x-stoplight": {
          "id": "arr8mqdncawmb"
        }
      }
    },
    "/api/v1/sessions/{session_id}/leaderboards-grouped-by-category": {
      "parameters": [
        {
          "schema": {
            "type": "string"
          },
          "name": "session_id",
          "in": "path",
          "required": true,
          "description": "Идентификатор сессии"
        }
      ],
      "get": {
        "summary": "Получить лучшие круги сессии сгруппированные по категориям",
        "tags": [
          "session"
        ],
        "responses": {
          "200": {
            "description": "OK",
            "headers": {},
            "content": {
              "application/json": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/SessionResultByCategory",
                    "x-stoplight": {
                      "id": "tfk94hy9ddwrg"
                    }
                  }
                }
              }
            }
          }
        },
        "operationId": "get-api-v1-sessions-session_id-leaderboards-grouped-by-category",
        "x-stoplight": {
          "id": "0tttip4ufqpud"
        }
      }
    }
  },
  "components": {
    "schemas": {
      "EventRegistrations": {
        "title": "EventRegistrations",
        "x-stoplight": {
          "id": "0c97rr6lzo7i9"
        },
        "type": "object",
        "required": [
          "event_id",
          "crews"
        ],
        "properties": {
          "event_id": {
            "type": "integer",
            "x-stoplight": {
              "id": "kqq91u3b648ld"
            },
            "format": "int64",
            "minimum": 1
          },
          "crews": {
            "type": "array",
            "x-stoplight": {
              "id": "6eiamytiar49k"
            },
            "items": {}
          }
        }
      },
      "EventRegistrationRequest": {
        "title": "EventRegistrationRequest",
        "x-stoplight": {
          "id": "kx88ea21fb2mf"
        },
        "type": "object",
        "required": [
          "pilot",
          "vehicle",
          "status"
        ],
        "properties": {
          "pilot": {
            "type": "object",
            "x-stoplight": {
              "id": "1sk5qn16bzaco"
            },
            "required": [
              "user_id",
              "full_name"
            ],
            "properties": {
              "user_id": {
                "type": "integer",
                "x-stoplight": {
                  "id": "lxhceoowuir3s"
                },
                "format": "int64",
                "minimum": 1
              },
              "full_name": {
                "type": "string",
                "x-stoplight": {
                  "id": "101vnw2xitgo4"
                }
              }
            }
          },
          "vehicle": {
            "type": "object",
            "x-stoplight": {
              "id": "84wkoubv6rw0w"
            },
            "required": [
              "vehicle_id",
              "model"
            ],
            "properties": {
              "vehicle_id": {
                "type": "integer",
                "x-stoplight": {
                  "id": "tocefopnzj41e"
                },
                "format": "int64",
                "minimum": 1
              },
              "model": {
                "type": "string",
                "x-stoplight": {
                  "id": "e5awnq2zg9l8k"
                }
              }
            }
          },
          "status": {
            "x-stoplight": {
              "id": "aklku5purp9gq"
            },
            "enum": [
              "CREATED",
              "APPROVED",
              "DECLINED"
            ]
          }
        },
        "description": "",
        "examples": [
          {
            "pilot": {
              "user_id": 1,
              "full_name": "string"
            },
            "vehicle": {
              "vehicle_id": 1,
              "model": "string"
            },
            "status": "CREATED"
          }
        ]
      },
      "EventRegistrationRequestsList": {
        "title": "EventRegistrationRequestsList",
        "x-stoplight": {
          "id": "2eoh7jixzp4kj"
        },
        "type": "object",
        "required": [
          "data",
          "size"
        ],
        "properties": {
          "data": {
            "type": "array",
            "x-stoplight": {
              "id": "2epu0d9lvtxhg"
            },
            "items": {
              "$ref": "#/components/schemas/EventRegistrationRequest",
              "x-stoplight": {
                "id": "plegcvlh6pn86"
              }
            }
          },
          "size": {
            "type": "integer",
            "x-stoplight": {
              "id": "u38hq0ltr1ma8"
            },
            "format": "int64",
            "minimum": 0
          }
        }
      },
      "CrewRegistrationRequest": {
        "title": "CrewRegistrationRequest",
        "x-stoplight": {
          "id": "jx86tylrqnfux"
        },
        "type": "object",
        "required": [
          "user_id",
          "vehicle_id",
          "category_id",
          "racing_number"
        ],
        "properties": {
          "user_id": {
            "type": "integer",
            "x-stoplight": {
              "id": "00kgib30ey6w8"
            },
            "minimum": 1,
            "format": "int64"
          },
          "vehicle_id": {
            "type": "integer",
            "x-stoplight": {
              "id": "epk8hnm3odclx"
            },
            "format": "int64",
            "minimum": 1
          },
          "category_id": {
            "type": "integer",
            "x-stoplight": {
              "id": "4291pqbphlq9c"
            },
            "format": "int32",
            "minimum": 1
          },
          "racing_number": {
            "type": "integer",
            "x-stoplight": {
              "id": "e08j6t7iypbpb"
            },
            "format": "int32",
            "minimum": 1,
            "maximum": 99
          }
        },
        "examples": [
          {
            "user_id": 1,
            "vehicle_id": 1,
            "category_id": 1,
            "racing_number": 1
          }
        ]
      },
      "EventSessionsList": {
        "title": "EventSessionsList",
        "x-stoplight": {
          "id": "9ir87zyzk5pda"
        },
        "type": "object",
        "examples": [
          {
            "data": [
              {
                "session_id": 1,
                "type_id": 1,
                "name": "string",
                "ordinal_number": 1
              }
            ]
          }
        ],
        "required": [
          "data"
        ],
        "properties": {
          "data": {
            "x-stoplight": {
              "id": "3lppe23legc7k"
            },
            "type": "array",
            "items": {
              "$ref": "#/components/schemas/EventSession",
              "x-stoplight": {
                "id": "xa5o8hmsjl5ui"
              }
            }
          }
        }
      },
      "LeaderBoardLapData": {
        "title": "LeaderBoardLapData",
        "x-stoplight": {
          "id": "ib4q2gsugt5q6"
        },
        "type": "object",
        "required": [
          "lap_id",
          "lap_time",
          "crew"
        ],
        "properties": {
          "lap_id": {
            "type": "integer",
            "x-stoplight": {
              "id": "r64y3yu4kb24c"
            },
            "format": "int64",
            "minimum": 1
          },
          "lap_time": {
            "type": "integer",
            "x-stoplight": {
              "id": "endlfbb2385dd"
            },
            "format": "int64"
          },
          "crew": {
            "$ref": "#/components/schemas/Crew",
            "x-stoplight": {
              "id": "r4ubu7nxysiwj"
            }
          }
        }
      },
      "UserProfile": {
        "title": "UserProfile",
        "x-stoplight": {
          "id": "yr7vjv4icsszp"
        },
        "allOf": [
          {
            "type": "object",
            "required": [
              "id"
            ],
            "properties": {
              "id": {
                "type": "integer",
                "x-stoplight": {
                  "id": "zckkt25nmbyva"
                },
                "format": "int64",
                "minimum": 1
              }
            }
          },
          {
            "$ref": "#/components/schemas/UserPersonalData",
            "x-stoplight": {
              "id": "thk8wki2x1brl"
            }
          },
          {
            "type": "object",
            "x-stoplight": {
              "id": "wxq8xyz2aglbu"
            },
            "properties": {
              "vehicles": {
                "x-stoplight": {
                  "id": "kcpk3rjycbn33"
                },
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/Vehicle",
                  "x-stoplight": {
                    "id": "l3wruovok564r"
                  }
                }
              }
            }
          },
          {
            "type": "object",
            "x-stoplight": {
              "id": "y83hbq7r6vkcm"
            },
            "required": [
              "created_at",
              "is_verified"
            ],
            "properties": {
              "created_at": {
                "type": "string",
                "x-stoplight": {
                  "id": "xc33oxpu40s50"
                },
                "format": "date-time"
              },
              "is_verified": {
                "type": "boolean",
                "x-stoplight": {
                  "id": "fgo45aozv7r8h"
                }
              }
            }
          }
        ],
        "examples": [
          {
            "id": 11,
            "first_name": "John",
            "last_name": "Turbo",
            "birth_date": "2006-08-24",
            "email": "john-turbo@example.com",
            "vehicles": [
              {
                "id": 16,
                "make": "Toyota",
                "model": "GR86",
                "year": 2023
              }
            ],
            "created_at": "2024-08-24T14:15:22Z",
            "is_verified": true
          }
        ]
      },
      "UserPersonalData": {
        "title": "UserPersonalData",
        "x-stoplight": {
          "id": "u1fabrbfiwtvc"
        },
        "type": "object",
        "examples": [
          {
            "first_name": "John",
            "last_name": "Turbo",
            "birth_date": "2006-08-24",
            "email": "john-turbo@example.com"
          }
        ],
        "required": [
          "email",
          "first_name",
          "last_name",
          "birth_date"
        ],
        "properties": {
          "email": {
            "type": "string",
            "x-stoplight": {
              "id": "sgzii3f8ioccs"
            },
            "format": "email"
          },
          "first_name": {
            "type": "string",
            "x-stoplight": {
              "id": "kjvhdwbuh2sdc"
            },
            "maxLength": 255
          },
          "last_name": {
            "type": "string",
            "x-stoplight": {
              "id": "9j8guxpgtc40s"
            },
            "maxLength": 255
          },
          "birth_date": {
            "type": "string",
            "x-stoplight": {
              "id": "2tcwboveoifix"
            },
            "format": "date"
          }
        }
      },
      "EventWithSessions": {
        "title": "EventWithSessions",
        "x-stoplight": {
          "id": "3c8w1q4douca6"
        },
        "type": "object",
        "required": [
          "id",
          "name",
          "date",
          "track"
        ],
        "properties": {
          "id": {
            "type": "integer",
            "x-stoplight": {
              "id": "8515a6w95fn11"
            },
            "format": "int64"
          },
          "name": {
            "type": "string",
            "x-stoplight": {
              "id": "3jka5rgbdc6pk"
            },
            "maxLength": 255
          },
          "date": {
            "type": "string",
            "x-stoplight": {
              "id": "dpkgr2zg0scwf"
            },
            "format": "date"
          },
          "track": {
            "$ref": "#/components/schemas/Track",
            "x-stoplight": {
              "id": "3ixe48qkegt2p"
            }
          },
          "sessions": {
            "type": "array",
            "x-stoplight": {
              "id": "yvi4m4ayqhwqz"
            },
            "items": {
              "$ref": "#/components/schemas/EventSession",
              "x-stoplight": {
                "id": "fby1c74r21c7k"
              }
            }
          }
        }
      },
      "LeaderBoard": {
        "title": "LeaderBoard",
        "x-stoplight": {
          "id": "inmuacofe3c2a"
        },
        "type": "object",
        "required": [
          "category_id",
          "category_name",
          "positions"
        ],
        "properties": {
          "category_id": {
            "type": "string",
            "x-stoplight": {
              "id": "gho6m43s1r7lv"
            }
          },
          "category_name": {
            "type": "string",
            "x-stoplight": {
              "id": "942z7rg6h1cam"
            }
          },
          "positions": {
            "type": "array",
            "x-stoplight": {
              "id": "mo7rzei41hjci"
            },
            "items": {
              "$ref": "#/components/schemas/LeaderBoardLapData",
              "x-stoplight": {
                "id": "iyhu4bugwkg19"
              }
            }
          }
        }
      },
      "CreateEventSessionRequest": {
        "title": "CreateEventSessionRequest",
        "x-stoplight": {
          "id": "ilibl8y5aye3m"
        },
        "type": "object",
        "required": [
          "session_name",
          "type_id",
          "laps_limit"
        ],
        "properties": {
          "session_name": {
            "type": "string",
            "x-stoplight": {
              "id": "bqm6sf7sve0wn"
            },
            "maxLength": 255
          },
          "type_id": {
            "x-stoplight": {
              "id": "1xghn8ehp54hc"
            },
            "type": "integer",
            "minimum": 1
          },
          "laps_limit": {
            "type": "integer",
            "x-stoplight": {
              "id": "e6veivi5ljcvo"
            },
            "minimum": 1
          }
        },
        "examples": [
          {
            "session_name": "Practice 1",
            "type_id": 1,
            "laps_limit": 999
          }
        ]
      },
      "SesionTypeEnum": {
        "title": "SesionTypeEnum",
        "x-stoplight": {
          "id": "9mbe3vz95e7ab"
        },
        "enum": [
          "PRACTICE",
          "COMPETITION"
        ]
      },
      "EventSession": {
        "title": "EventSession",
        "x-stoplight": {
          "id": "fcueg4mckkiu0"
        },
        "type": "object",
        "examples": [
          {
            "session_id": 138,
            "ordinal_number": 1,
            "session_type": {
              "id": 1,
              "type": "PRACTICE"
            },
            "session_name": "First practice",
            "laps_limit": 999
          }
        ],
        "required": [
          "session_id",
          "ordinal_number",
          "session_type",
          "session_name",
          "laps_limit"
        ],
        "properties": {
          "session_id": {
            "type": "integer",
            "x-stoplight": {
              "id": "3zruytbzzytc8"
            },
            "format": "int64",
            "minimum": 1
          },
          "ordinal_number": {
            "type": "integer",
            "x-stoplight": {
              "id": "yvxpqb940y6ud"
            },
            "format": "int32"
          },
          "session_type": {
            "$ref": "#/components/schemas/SessionType",
            "x-stoplight": {
              "id": "ult6bj7zgrqix"
            }
          },
          "session_name": {
            "type": "string",
            "x-stoplight": {
              "id": "vjv0bdmve9gb2"
            }
          },
          "laps_limit": {
            "type": "integer",
            "x-stoplight": {
              "id": "33ckizjd86vl9"
            },
            "format": "int32"
          }
        }
      },
      "SessionType": {
        "title": "SessionType",
        "x-stoplight": {
          "id": "eknf1g8yjfnu5"
        },
        "type": "object",
        "examples": [
          {
            "id": 1,
            "type": "PRACTICE"
          }
        ],
        "required": [
          "id",
          "type"
        ],
        "properties": {
          "id": {
            "type": "integer",
            "x-stoplight": {
              "id": "a42zz9qkwwjfo"
            },
            "format": "int32",
            "minimum": 1
          },
          "type": {
            "$ref": "#/components/schemas/SesionTypeEnum",
            "x-stoplight": {
              "id": "cna4lh7oj1rt8"
            }
          }
        }
      },
      "CreateTrackRequest": {
        "title": "CreateTrackRequest",
        "x-stoplight": {
          "id": "z8r0rdibrvvrx"
        },
        "type": "object",
        "examples": [
          {
            "track_name": "Moscow Raceway"
          }
        ],
        "required": [
          "track_name"
        ],
        "properties": {
          "track_name": {
            "type": "string",
            "x-stoplight": {
              "id": "qn55k94w0y3p3"
            }
          }
        }
      },
      "Track": {
        "title": "Track",
        "x-stoplight": {
          "id": "evjsfmpdn4ouc"
        },
        "type": "object",
        "required": [
          "id",
          "track_name"
        ],
        "properties": {
          "id": {
            "type": "integer",
            "x-stoplight": {
              "id": "cofitvad874a9"
            },
            "format": "int64"
          },
          "track_name": {
            "type": "string",
            "x-stoplight": {
              "id": "lsy0grx4r5dv9"
            },
            "maxLength": 255
          }
        },
        "examples": [
          {
            "id": 12,
            "track_name": "Moscow Raceway"
          }
        ]
      },
      "GroupedCrews": {
        "type": "object",
        "x-examples": {
          "Example 1": {
            "data": [
              {
                "category": {
                  "id": 3,
                  "name": "STREET",
                  "max_power": 120,
                  "wheel_drive_type": "AWD"
                },
                "crews": [
                  {
                    "id": "349121c4-21c6-4f74-91d9-89b9f1ef4692",
                    "pilot_name": "Bi-Turbo Mike",
                    "vehicle_name": "Toyota Supra",
                    "starting_number": 22
                  },
                  {
                    "id": "22ae4c67-6177-4964-9369-da62495043ca",
                    "pilot_name": "Turbo John",
                    "vehicle_name": "Toyota GR86",
                    "starting_number": 33
                  }
                ]
              },
              {
                "category": {
                  "id": 5,
                  "name": "SPORT",
                  "max_power": 250,
                  "wheel_drive_type": "FWD"
                },
                "crews": []
              },
              {
                "category": {
                  "id": 2,
                  "name": "STREET",
                  "max_power": 120,
                  "wheel_drive_type": "FWD"
                },
                "crews": []
              },
              {
                "category": {
                  "id": 7,
                  "name": "EXTREME",
                  "max_power": 1000000,
                  "wheel_drive_type": "RWD"
                },
                "crews": []
              }
            ]
          }
        },
        "examples": [
          {
            "data": [
              {
                "category": {
                  "id": 2,
                  "name": "STREET 120",
                  "max_power": 120,
                  "wheel_drive_type": "RWD"
                },
                "crews": [
                  {
                    "id": "21",
                    "pilot_name": "Turbo John",
                    "vehicle_name": "Toyota GR86",
                    "starting_number": 33
                  }
                ]
              }
            ]
          }
        ],
        "required": [
          "data"
        ],
        "properties": {
          "data": {
            "type": "array",
            "items": {
              "type": "object",
              "required": [
                "category",
                "crews"
              ],
              "properties": {
                "category": {
                  "type": "object",
                  "required": [
                    "id",
                    "name",
                    "max_power",
                    "wheel_drive_type"
                  ],
                  "properties": {
                    "id": {
                      "type": "integer"
                    },
                    "name": {
                      "type": "string"
                    },
                    "max_power": {
                      "type": "integer"
                    },
                    "wheel_drive_type": {
                      "type": "string"
                    }
                  }
                },
                "crews": {
                  "type": "array",
                  "items": {
                    "type": "object",
                    "required": [
                      "id",
                      "pilot_name",
                      "vehicle_name",
                      "starting_number"
                    ],
                    "properties": {
                      "id": {
                        "type": "string"
                      },
                      "pilot_name": {
                        "type": "string"
                      },
                      "vehicle_name": {
                        "type": "string"
                      },
                      "starting_number": {
                        "type": "integer"
                      }
                    }
                  }
                }
              }
            }
          }
        }
      },
      "Crew": {
        "title": "Crew",
        "x-stoplight": {
          "id": "6c217j49o3drg"
        },
        "type": "object",
        "required": [
          "id",
          "pilot_name",
          "vehicle_name",
          "racing_number"
        ],
        "properties": {
          "id": {
            "type": "string",
            "x-stoplight": {
              "id": "bouil1igvberq"
            },
            "format": "uuid"
          },
          "pilot_name": {
            "type": "string",
            "x-stoplight": {
              "id": "ct59n3928tfnw"
            }
          },
          "vehicle_name": {
            "type": "string",
            "x-stoplight": {
              "id": "8lbnqork225jk"
            }
          },
          "racing_number": {
            "type": "integer",
            "x-stoplight": {
              "id": "ono676iuiausb"
            },
            "minimum": 1,
            "maximum": 99,
            "format": "int32"
          }
        },
        "examples": [
          {
            "id": "497f6eca-6276-4993-bfeb-53cbbbba6f08",
            "pilot_name": "Turbo John",
            "vehicle_name": "Toyota GR86",
            "racing_number": 56
          }
        ]
      },
      "UserEventRegistrationRequest": {
        "title": "UserEventRegistrationRequest",
        "x-stoplight": {
          "id": "9nafdi07bzo31"
        },
        "type": "object",
        "required": [
          "user_id",
          "vehicle_id",
          "catagory_id",
          "race_number"
        ],
        "properties": {
          "user_id": {
            "type": "string",
            "x-stoplight": {
              "id": "2zkbymu0z1lkw"
            },
            "format": "uuid"
          },
          "vehicle_id": {
            "type": "integer",
            "x-stoplight": {
              "id": "j2ly1ods4awka"
            },
            "format": "int64",
            "minimum": 1
          },
          "catagory_id": {
            "type": "integer",
            "x-stoplight": {
              "id": "7erwap0wf92p1"
            },
            "format": "int32",
            "minimum": 1
          },
          "race_number": {
            "type": "integer",
            "x-stoplight": {
              "id": "7sjaehje07jrd"
            },
            "minimum": 1,
            "maximum": 99
          }
        },
        "examples": [
          {
            "user_id": "a169451c-8525-4352-b8ca-070dd449a1a5",
            "vehicle_id": 56,
            "catagory_id": 3,
            "race_number": 17
          }
        ]
      },
      "CreateEventRequest": {
        "title": "CreateEventRequest",
        "x-stoplight": {
          "id": "j7x3wqlpjr0n4"
        },
        "type": "object",
        "required": [
          "name",
          "date",
          "track_id",
          "category_ids"
        ],
        "properties": {
          "name": {
            "type": "string",
            "x-stoplight": {
              "id": "q55uuihhc0lfo"
            },
            "maxLength": 255
          },
          "date": {
            "type": "string",
            "x-stoplight": {
              "id": "0cqrws4ym5kw9"
            },
            "format": "date"
          },
          "track_id": {
            "type": "integer",
            "x-stoplight": {
              "id": "r8tvksj7e32k8"
            },
            "format": "int64",
            "minimum": 1
          },
          "category_ids": {
            "type": "array",
            "x-stoplight": {
              "id": "nzuk8n0i9y2zs"
            },
            "items": {
              "x-stoplight": {
                "id": "qijed7faj5f83"
              },
              "type": "integer",
              "minimum": 1
            }
          }
        },
        "examples": [
          {
            "name": "Spring Race Weekend",
            "date": "2019-08-24",
            "track_id": 1,
            "category_ids": [
              1,
              3,
              5
            ]
          }
        ]
      },
      "EventPage": {
        "type": "object",
        "x-stoplight": {
          "id": "228134cfa7980"
        },
        "x-examples": {
          "Example 1": {
            "data": {
              "id": -9007199254740991,
              "name": "string",
              "date": "2019-08-24",
              "track": {
                "id": 0,
                "name": "string"
              }
            }
          }
        },
        "additionalProperties": false,
        "required": [
          "events",
          "pagination"
        ],
        "properties": {
          "events": {
            "type": "array",
            "x-stoplight": {
              "id": "k0i1s25ygqcj6"
            },
            "items": {
              "$ref": "#/components/schemas/Event",
              "x-stoplight": {
                "id": "fdf7hwb3aosz5"
              }
            }
          },
          "pagination": {
            "type": "object",
            "x-stoplight": {
              "id": "1bwfd3k6vre8z"
            },
            "required": [
              "offset",
              "page_size_limit",
              "elements",
              "has_previous",
              "has_next"
            ],
            "properties": {
              "offset": {
                "type": "integer",
                "x-stoplight": {
                  "id": "k7ckht8pc17zd"
                },
                "format": "int32"
              },
              "page_size_limit": {
                "type": "integer",
                "x-stoplight": {
                  "id": "ur0h1hqw96nco"
                },
                "format": "int32",
                "minimum": 0
              },
              "elements": {
                "type": "integer",
                "x-stoplight": {
                  "id": "kpk8qozgccwk9"
                },
                "format": "int32",
                "minimum": 0
              },
              "has_previous": {
                "type": "boolean",
                "x-stoplight": {
                  "id": "ivgy80pqqukhg"
                }
              },
              "has_next": {
                "type": "boolean",
                "x-stoplight": {
                  "id": "n81hsbqkdrx6s"
                }
              }
            }
          }
        },
        "examples": [
          {
            "events": [
              {
                "id": 1,
                "date": "2019-08-24",
                "name": "Spring Race Weekend 1",
                "track": {
                  "id": 12,
                  "track_name": "Moscow Raceway"
                },
                "categories": [
                  {
                    "id": 1,
                    "name": "STREET 120",
                    "max_power_limit": 120,
                    "wheel_drive_type": "RWD"
                  },
                  {
                    "id": 2,
                    "name": "STREET 120",
                    "max_power_limit": 120,
                    "wheel_drive_type": "FWD"
                  }
                ]
              }
            ],
            "pagination": {
              "offset": 0,
              "page_size_limit": 10,
              "elements": 1,
              "has_previous": false,
              "has_next": false
            }
          }
        ]
      },
      "EventResults": {
        "title": "EventResults",
        "x-stoplight": {
          "id": "lz64cbfq93iqq"
        },
        "type": "object",
        "properties": {
          "data": {
            "type": "array",
            "x-stoplight": {
              "id": "zktd5o0rea3rt"
            },
            "items": {
              "$ref": "#/components/schemas/CrewEventResult",
              "x-stoplight": {
                "id": "3c3tkvqr2vcsm"
              }
            }
          }
        },
        "examples": [
          {
            "data": [
              {
                "crew": {
                  "id": "497f6eca-6276-4993-bfeb-53cbbbba6f08",
                  "pilot_name": "Turbo John",
                  "vehicle_name": "Toyota GR86",
                  "racing_number": 25
                },
                "competition_times": [
                  "01:47.155"
                ],
                "summary_time": "01:47.155"
              }
            ]
          }
        ]
      },
      "CrewEventResult": {
        "title": "CrewEventResult",
        "x-stoplight": {
          "id": "1b3e7hyakw8tk"
        },
        "type": "object",
        "examples": [
          {
            "crew": {
              "id": "497f6eca-6276-4993-bfeb-53cbbbba6f08",
              "pilot_name": "Turbo John",
              "vehicle_name": "Toyota GR86",
              "racing_number": 25
            },
            "competition_times": [
              "01:47.155"
            ],
            "summary_time": "01:47.155"
          }
        ],
        "required": [
          "crew",
          "competition_times",
          "summary_time"
        ],
        "properties": {
          "crew": {
            "type": "object",
            "x-stoplight": {
              "id": "gexxrj5635qfs"
            },
            "required": [
              "id",
              "pilot_name",
              "vehicle_name",
              "racing_number"
            ],
            "properties": {
              "id": {
                "type": "string",
                "x-stoplight": {
                  "id": "3u6hrxgyhlyia"
                },
                "format": "uuid"
              },
              "pilot_name": {
                "type": "string",
                "x-stoplight": {
                  "id": "sheo1h6xajefi"
                }
              },
              "vehicle_name": {
                "type": "string",
                "x-stoplight": {
                  "id": "9re3g09v8mt99"
                }
              },
              "racing_number": {
                "type": "integer",
                "x-stoplight": {
                  "id": "br1a10277wj4g"
                },
                "format": "int32",
                "minimum": 1,
                "maximum": 99
              }
            }
          },
          "competition_times": {
            "type": "array",
            "x-stoplight": {
              "id": "chfv8xjw9buyj"
            },
            "items": {
              "x-stoplight": {
                "id": "nxq6yw7ey6r5m"
              },
              "type": "string"
            }
          },
          "summary_time": {
            "type": "string",
            "x-stoplight": {
              "id": "bqnkz2xrmp19v"
            }
          }
        }
      },
      "Event": {
        "title": "Event",
        "x-stoplight": {
          "id": "vy2j50tg3cgkc"
        },
        "type": "object",
        "required": [
          "id",
          "date",
          "name",
          "track",
          "categories"
        ],
        "properties": {
          "id": {
            "type": "integer",
            "x-stoplight": {
              "id": "6swxsm8ej25rx"
            },
            "format": "int64",
            "minimum": 1
          },
          "date": {
            "type": "string",
            "x-stoplight": {
              "id": "rg7lo3q8jfpar"
            },
            "format": "date"
          },
          "name": {
            "type": "string",
            "x-stoplight": {
              "id": "f8fk9kcaft0p9"
            }
          },
          "track": {
            "$ref": "#/components/schemas/Track",
            "x-stoplight": {
              "id": "xfcqbpc6tt8sm"
            }
          },
          "categories": {
            "type": "array",
            "x-stoplight": {
              "id": "wisdhmvkzwyth"
            },
            "items": {
              "$ref": "#/components/schemas/Category",
              "x-stoplight": {
                "id": "r90073q1grexx"
              }
            }
          }
        },
        "examples": [
          {
            "id": 1,
            "date": "2019-08-24",
            "name": "Spring Race Weekend 1",
            "track": {
              "id": 12,
              "track_name": "Moscow Raceway"
            },
            "categories": [
              {
                "id": 1,
                "name": "STREET 120",
                "max_power_limit": 120,
                "wheel_drive_type": "RWD"
              },
              {
                "id": 2,
                "name": "STREET 120",
                "max_power_limit": 120,
                "wheel_drive_type": "FWD"
              }
            ]
          }
        ]
      },
      "Category": {
        "type": "object",
        "x-examples": {
          "Example 1": {
            "id": 1,
            "name": "STREET",
            "max_power_limit": 120,
            "wheel_drive_type": "RWD"
          }
        },
        "examples": [
          {
            "id": 1,
            "name": "STREET 120",
            "max_power_limit": 120,
            "wheel_drive_type": "RWD"
          }
        ],
        "required": [
          "id",
          "name",
          "max_power_limit",
          "wheel_drive_type"
        ],
        "properties": {
          "id": {
            "type": "integer",
            "format": "int32",
            "minimum": 1
          },
          "name": {
            "type": "string",
            "maxLength": 255
          },
          "max_power_limit": {
            "type": "integer",
            "format": "int32",
            "minimum": 1
          },
          "wheel_drive_type": {
            "type": "string",
            "maxLength": 255
          }
        }
      },
      "User": {
        "title": "User",
        "x-stoplight": {
          "id": "qypnfo0q5xebm"
        },
        "allOf": [
          {
            "type": "object",
            "x-stoplight": {
              "id": "znhchnj2u0p5m"
            },
            "required": [
              "id"
            ],
            "properties": {
              "id": {
                "type": "string",
                "x-stoplight": {
                  "id": "jxmmvn4rb2sez"
                },
                "format": "uuid"
              }
            }
          },
          {
            "$ref": "#/components/schemas/UserPersonalData",
            "x-stoplight": {
              "id": "7wypdt6o8nr01"
            }
          }
        ],
        "examples": [
          {
            "id": "497f6eca-6276-4993-bfeb-53cbbbba6f08",
            "first_name": "John",
            "last_name": "Turbo",
            "birth_date": "2006-08-24",
            "email": "john-turbo@example.com"
          }
        ]
      },
      "Vehicle": {
        "title": "Vehicle",
        "x-stoplight": {
          "id": "imhzpr0lhpxuy"
        },
        "type": "object",
        "required": [
          "id",
          "make",
          "model",
          "year",
          "user_id"
        ],
        "properties": {
          "id": {
            "type": "integer",
            "x-stoplight": {
              "id": "vek4fy8wkvgjz"
            },
            "format": "int64"
          },
          "make": {
            "type": "string",
            "x-stoplight": {
              "id": "0aofvdgsc6vw6"
            },
            "maxLength": 255
          },
          "model": {
            "type": "string",
            "x-stoplight": {
              "id": "962er9r3fwtgx"
            },
            "maxLength": 255
          },
          "year": {
            "type": "integer",
            "x-stoplight": {
              "id": "jpbmoq5zdea77"
            },
            "format": "int32"
          },
          "user_id": {
            "type": "string",
            "x-stoplight": {
              "id": "nldstdm0iezpl"
            },
            "format": "uuid"
          }
        },
        "examples": [
          {
            "id": 1,
            "make": "Toyota",
            "model": "GR86",
            "year": 2023,
            "user_id": "a169451c-8525-4352-b8ca-070dd449a1a5"
          }
        ]
      },
      "CreateVehicleRequest": {
        "title": "CreateVehicleRequest",
        "x-stoplight": {
          "id": "6dmbluefe777g"
        },
        "type": "object",
        "required": [
          "user_id",
          "make",
          "model",
          "year"
        ],
        "properties": {
          "user_id": {
            "type": "string",
            "x-stoplight": {
              "id": "cyg6022ctwecm"
            },
            "format": "uuid"
          },
          "make": {
            "type": "string",
            "x-stoplight": {
              "id": "p4pesdjblgncj"
            },
            "maxLength": 255
          },
          "model": {
            "type": "string",
            "x-stoplight": {
              "id": "dpv2kv27pe839"
            },
            "maxLength": 255
          },
          "year": {
            "type": "integer",
            "x-stoplight": {
              "id": "8jn2k0qmca0tx"
            },
            "format": "int64"
          }
        },
        "examples": [
          {
            "user_id": "a169451c-8525-4352-b8ca-070dd449a1a5",
            "make": "Toyota",
            "model": "GR86",
            "year": 2023
          }
        ]
      },
      "JwtSet": {
        "type": "object",
        "x-examples": {
          "Example 1": {
            "accessToken": "eyJ",
            "refreshToken": "eyJ"
          }
        },
        "examples": [
          {
            "access_token": "access.token.value",
            "refresh_token": "refresh.token.value"
          }
        ],
        "required": [
          "access_token",
          "refresh_token"
        ],
        "properties": {
          "access_token": {
            "type": "string",
            "x-stoplight": {
              "id": "a6hbbj1hmhfam"
            }
          },
          "refresh_token": {
            "type": "string",
            "x-stoplight": {
              "id": "9qsdmp0qzr578"
            }
          }
        }
      },
      "RefreshTokenrequest": {
        "title": "RefreshTokenRequest",
        "x-stoplight": {
          "id": "cwrep5vjtqod5"
        },
        "type": "object",
        "required": [
          "refresh_token"
        ],
        "properties": {
          "refresh_token": {
            "type": "string",
            "x-stoplight": {
              "id": "cjbefv9r1whmn"
            }
          }
        },
        "examples": [
          {
            "refresh_token": "refresh.token.value"
          }
        ],
        "description": ""
      },
      "LoginForm": {
        "title": "LoginForm",
        "x-stoplight": {
          "id": "adhzyi3w9w7dn"
        },
        "type": "object",
        "required": [
          "email",
          "password"
        ],
        "properties": {
          "email": {
            "type": "string",
            "x-stoplight": {
              "id": "cd91f4mkgqto4"
            },
            "format": "email"
          },
          "password": {
            "type": "string",
            "x-stoplight": {
              "id": "1ruhad4645yv1"
            },
            "format": "password"
          }
        },
        "examples": [
          {
            "email": "john-turbo@example.com",
            "password": "pa$$word"
          }
        ]
      },
      "UserRegistrationRequest": {
        "title": "UserRegistrationRequest",
        "x-stoplight": {
          "id": "7qzmkd8efhe2p"
        },
        "allOf": [
          {
            "x-stoplight": {
              "id": "4rdhz71w056dl"
            },
            "type": "object",
            "required": [
              "password"
            ],
            "properties": {
              "password": {
                "type": "string",
                "x-stoplight": {
                  "id": "f9x95okfzhq8t"
                },
                "format": "password"
              }
            }
          },
          {
            "$ref": "#/components/schemas/UserPersonalData",
            "x-stoplight": {
              "id": "dgo7l4o1bx6kc"
            }
          }
        ],
        "examples": [
          {
            "password": "pa$$word",
            "first_name": "John",
            "last_name": "Turbo",
            "birth_date": "2006-08-24",
            "email": "john-turbo@example.com"
          }
        ]
      },
      "LapsSaveResult": {
        "title": "LapsSaveResult",
        "x-stoplight": {
          "id": "kp8o4vrr23oxo"
        },
        "type": "object",
        "required": [
          "successful",
          "failed",
          "total"
        ],
        "properties": {
          "successful": {
            "type": "integer",
            "x-stoplight": {
              "id": "9vuppal3w5fvq"
            },
            "minimum": 0
          },
          "failed": {
            "type": "integer",
            "x-stoplight": {
              "id": "2duc5ov89urhi"
            },
            "minimum": 0
          },
          "total": {
            "type": "integer",
            "x-stoplight": {
              "id": "qrxbqyyvj2lpw"
            },
            "minimum": 0
          }
        },
        "examples": [
          {
            "successful": 120,
            "failed": 0,
            "total": 120
          }
        ]
      },
      "SessionLaps": {
        "title": "SessionLaps",
        "x-stoplight": {
          "id": "kcal05hdckkcc"
        },
        "type": "object",
        "examples": [
          {
            "data": [
              {
                "crew": {
                  "id": "497f6eca-6276-4993-bfeb-53cbbbba6f08",
                  "pilot_name": "Turbo John",
                  "vehicle_name": "Toyota GR86",
                  "racing_number": 56
                },
                "lap_times": [
                  "01:53.575",
                  "01:49.491"
                ]
              }
            ]
          }
        ],
        "required": [
          "data"
        ],
        "properties": {
          "data": {
            "type": "array",
            "x-stoplight": {
              "id": "0i2h77j2l4q8l"
            },
            "items": {
              "$ref": "#/components/schemas/CrewSessionLaps",
              "x-stoplight": {
                "id": "eenhm3lbq80j6"
              }
            }
          }
        }
      },
      "CrewSessionLaps": {
        "title": "CrewSessionLaps",
        "x-stoplight": {
          "id": "pwwkcznr4ctbq"
        },
        "type": "object",
        "examples": [
          {
            "crew": {
              "id": "497f6eca-6276-4993-bfeb-53cbbbba6f08",
              "pilot_name": "Turbo John",
              "vehicle_name": "Toyota GR86",
              "racing_number": 56
            },
            "lap_times": [
              "01:53.575",
              "01:49.491"
            ]
          }
        ],
        "required": [
          "crew",
          "lap_times"
        ],
        "properties": {
          "crew": {
            "$ref": "#/components/schemas/Crew",
            "x-stoplight": {
              "id": "xyz4tph1h0xcj"
            }
          },
          "lap_times": {
            "type": "array",
            "x-stoplight": {
              "id": "whyxqjvh9req7"
            },
            "items": {
              "x-stoplight": {
                "id": "dyu7vx70x34vq"
              },
              "type": "string"
            }
          }
        }
      },
      "SessionResultByCategory": {
        "title": "SessionResultByCategory",
        "x-stoplight": {
          "id": "ryduwsnkr01rs"
        },
        "type": "object",
        "required": [
          "classification_category",
          "data"
        ],
        "properties": {
          "classification_category": {
            "$ref": "#/components/schemas/Category",
            "x-stoplight": {
              "id": "ticgoz3cy7r0u"
            }
          },
          "data": {
            "type": "array",
            "x-stoplight": {
              "id": "yiw56eep78v1o"
            },
            "items": {
              "$ref": "#/components/schemas/CrewSessionLaps",
              "x-stoplight": {
                "id": "yq74i40hljman"
              }
            }
          }
        }
      },
      "EventResultByCategory": {
        "title": "EventResultByCategory",
        "x-stoplight": {
          "id": "ar8f1duy3c7zo"
        },
        "type": "object",
        "required": [
          "classification_category",
          "data"
        ],
        "properties": {
          "classification_category": {
            "$ref": "#/components/schemas/Category",
            "x-stoplight": {
              "id": "86x8qmpuko46r"
            }
          },
          "data": {
            "type": "array",
            "x-stoplight": {
              "id": "nxvl7hvl9czlm"
            },
            "items": {
              "$ref": "#/components/schemas/CrewEventResult",
              "x-stoplight": {
                "id": "4lg5w44pgo0p2"
              }
            }
          }
        }
      }
    },
    "responses": {},
    "parameters": {
      "sessionFilter": {
        "name": "session_filter",
        "in": "query",
        "required": false,
        "schema": {
          "type": "integer",
          "format": "int64",
          "minimum": 1
        },
        "description": "Идентификатор сессии для фильтрации результатов по конкретной сессии"
      }
    },
    "securitySchemes": {}
  }
}