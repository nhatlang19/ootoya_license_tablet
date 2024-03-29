package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.model.Section;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SectionAPI extends AbstractAPI {

    public SectionAPI(Context context) throws Exception {
        super(context);
    }

    public ArrayList<Section> getSection(String s) throws Exception {
        setMethod(METHOD_GET_SECTION);
        ArrayList<Section> sections = new ArrayList<Section>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("section", s);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) ((SoapObject) response).getProperty("diffgram");
        if (soapObject != null) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");
            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject table = (SoapObject) webServiceResponse
                        .getProperty(i);

                Section section = new Section();
                section.setId(table.getProperty("Section").toString());
                section.setName(table.getProperty("Description1").toString());
                section.setFloorPlan(table.getProperty("FloorPlan").toString());

                sections.add(section);
            }
        }
        return sections;
    }
}
