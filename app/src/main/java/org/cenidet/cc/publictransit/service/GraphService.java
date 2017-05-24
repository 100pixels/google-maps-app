package org.cenidet.cc.publictransit.service;


import android.content.Context;

import org.cenidet.cc.publictransit.dao.GraphDAO;
import org.cenidet.cc.publictransit.graph.Grafo;

public class GraphService {

    private GraphDAO graphDAO;
    private Context context;

    public GraphService(Context context){
        this.context = context;
        graphDAO = new GraphDAO(this.context);
    }

    public Grafo initGrafo(){
        return graphDAO.getVerticesGrafo();
    }

}
