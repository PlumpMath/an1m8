(ns an1m8.routes.home

            (:require
                      [clojure.java.io]
                      [clj-time.coerce :as tc]
                      [an1m8.layout :as layout]
                      [an1m8.util :as util]
                      [compojure.core :refer :all]
                      [noir.response :refer [edn xml]]
                      [clojure.pprint :refer [pprint]]
                      [noir.io :as io]
                      [noir.response :as resp]
                      [noir.util.middleware :refer [app-handler]]
                      [ring.util.response :refer [content-type]]
                      [selmer.parser :refer [render-file]]))

(defonce image-path "/tmp/images")

(defonce config-path "/tmp/config/")


(defn- render[args]
  (layout/render "an1m8.html" args))


(defn home-page []
      (render {
               ;:docs (util/md->html "/md/docs.md")
               :svg "<svg id=\"circle\" height=\"240\" width=\"240\">
                    <circle cx=\"120\" cy=\"120\" r=\"90\"
                            stroke=\"black\" stroke-width=\"45\" fill=\"rgba(255,0,0,0.77)\"
                            />
                    Sorry, your browser does not support inline SVG.
                </svg>"
              }))

(defn upload-page []
  (layout/render "upload.html"))

(defn debug-page []
  (layout/render "lt.html"))

(defn list-all-svg []
    {:images
     (drop 1 (for [file (file-seq (clojure.java.io/file image-path))] (.getName file)))})


(defroutes home-routes
  (GET "/" [] (home-page))

  (GET "/lt" [] (debug-page))

  ; (GET "/index" [] (index-page))
  (GET "/upload" [] (upload-page))

  (POST "/upload" [file]
      (let [{fname :filename} file
            timed-name (str fname "-" (System/currentTimeMillis))
            file-url (str "/files/" timed-name)
            res (assoc file :filename timed-name)]
         (io/upload-file image-path res)
         (resp/redirect file-url)))

  (GET "/files/:filename" [filename]
       (xml (slurp (str image-path filename))))


  (GET "/files/" []
       (edn (list-all-svg)))

  ;; hardcoded animations
  (GET "/gallery/" []
       (edn [
             ["/svg/logo_light.svg" {:total 100
                                     ;:prop :stroke
                                     :layers {"#A path" {:total 10
                                                         :timing-f {:id :const
                                                                    :duration 100
                                                                  }
                                                         :prop :stroke
                                                         }
                                              "#N path" {:total 100
                                                         :prop :stroke
                                                         :timing-f {:id :const
                                                                   :duration 50}
                                                         }
                                              "[id='1'] path" {:total 75
                                                               :prop :stroke
                                                               :timing-f {:id :sin
                                                                    :duration 100
                                                                  }
                                                               }
                                              "#M path" {:prop :stroke}
                                              "[id='8'] path" {:total 100
                                                               :prop :stroke
                                                               :timing-f {:id :ln
                                                                    :duration 100
                                                                  }
                                                               }}
                                     }]
             ["/svg/logo.svg" {:total 50
                                  :layers {"path"
                                           {:total 10
                                            :prop :stroke
                                            }}
                                 }]

             ["/svg/logo_3d.svg" {:total 50
                                  :layers {"#A_1 path" {:total 10
                    :timing-f {:id :const
                               :duration 100
                               }
                    :prop :stroke
                    }
         "#N_1 path" {:total 100
                    :prop :stroke
                    :timing-f {:id :const
                               :duration 50}
                    }
         "[id='1_1'] path" {:total 75
                          :prop :stroke
                          :timing-f {:id :sin
                                     :duration 100
                                     }
                          }
         "#M_1 path" {:prop :stroke}
         "[id='8_1'] path" {:total 100
                          :prop :stroke
                          :timing-f {:id :ln
                                     :duration 100
                                     }
                          }
         "#A_2 path" {:total 10
                    :timing-f {:id :const
                               :duration 100
                               }
                    :prop :stroke
                    }
         "#N_2 path" {:total 100
                    :prop :stroke
                    :timing-f {:id :const
                               :duration 50}
                    }
         "[id='1_2'] path" {:total 75
                          :prop :stroke
                          :timing-f {:id :sin
                                     :duration 100
                                     }
                          }
         "#M_2 path" {:prop :stroke}
         "[id='8_2'] path" {:total 100
                          :prop :stroke
                          :timing-f {:id :ln
                                     :duration 100
                                     }
                          }
         "#A_3 path" {:total 10
                    :timing-f {:id :const
                               :duration 100
                               }
                    :prop :stroke
                    }
         "#N_3 path" {:total 100
                    :prop :stroke
                    :timing-f {:id :const
                               :duration 50}
                    }
         "[id='1_3'] path" {:total 75
                          :prop :stroke
                          :timing-f {:id :sin
                                     :duration 100
                                     }
                          }
         "#M_3 path" {:prop :stroke}
         "[id='8_3'] path" {:total 100
                          :prop :stroke
                          :timing-f {:id :ln
                                     :duration 100
                                     }
                          }
         }
                                  }]




             ;["/svg/1.svg" {}]
             ;["/svg/2.svg" {}]
             ]))


)


