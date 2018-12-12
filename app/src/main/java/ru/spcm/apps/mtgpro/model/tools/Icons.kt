package ru.spcm.apps.mtgpro.model.tools


import ru.spcm.apps.mtgpro.R
import java.util.HashMap


/**
 * Иконки сетов
 * Created by gen on 28.06.2018.
 */

object Icons {

    val list: MutableMap<String, Int> = HashMap()

    private val colors = mutableMapOf(
            "Common" to R.color.rarityCommon,
            "Uncommon" to R.color.rarityUncommon,
            "Rare" to R.color.rarityRare,
            "Mythic Rare" to R.color.rarityMythicRare,
            "Special" to R.color.rarityMythicRare
    )

    init {

        //Ravnica Allegiance 2019
        list["RNA"] = R.drawable.ic_rna

        //Guilds of Ravnica 2018
        list["GRN"] = R.drawable.ic_grn

        //Battlebond 2018
        list["BBD"] = R.drawable.ic_bbd

        //Dominaria 2018
        list["DOM"] = R.drawable.ic_dom

        //Ixalan Block 2017-2018
        list["RIX"] = R.drawable.ic_rix
        list["XLN"] = R.drawable.ic_xln

        //Amonkhet Block 2017
        list["HOU"] = R.drawable.ic_hou
        list["AKH"] = R.drawable.ic_akh
        list["MPS_AKH"] = R.drawable.ic_mps_akh

        //Kaladesh Block 2016-2017
        list["AER"] = R.drawable.ic_aer
        list["KLD"] = R.drawable.ic_kld
        list["MPS"] = R.drawable.ic_mps

        //Shadows over Innistrad Block 2016
        list["EMN"] = R.drawable.ic_emn
        list["SOI"] = R.drawable.ic_soi

        //Battle For Zendikar Block 2015-2016
        list["OGW"] = R.drawable.ic_ogw
        list["BFZ"] = R.drawable.ic_bfz
        list["EXP"] = R.drawable.ic_exp

        //Magic Origins 2015
        list["ORI"] = R.drawable.ic_ori

        //Khans of Tarkir Block 2014-2015
        list["DTK"] = R.drawable.ic_dtk
        list["FRF"] = R.drawable.ic_frf
        list["KTK"] = R.drawable.ic_ktk
        list["FRF_UGIN"] = R.drawable.ic_frf

        //Theros Block 2013-2014
        list["JOU"] = R.drawable.ic_jou
        list["BNG"] = R.drawable.ic_bng
        list["THS"] = R.drawable.ic_ths

        //Return to Ravnica Block 2012-2013
        list["DGM"] = R.drawable.ic_dgm
        list["GTC"] = R.drawable.ic_gtc
        list["RTR"] = R.drawable.ic_rtr

        //Innistrad Block 2011-2012
        list["AVR"] = R.drawable.ic_avr
        list["DKA"] = R.drawable.ic_dka
        list["ISD"] = R.drawable.ic_isd

        //Scars of Mirrodin Block 2010-2011
        list["NPH"] = R.drawable.ic_nph
        list["SOM"] = R.drawable.ic_som
        list["MBS"] = R.drawable.ic_mbs

        //Zendikar Block 2009-2010
        list["ROE"] = R.drawable.ic_roe
        list["WWK"] = R.drawable.ic_wwk
        list["ZEN"] = R.drawable.ic_zen

        //Shards of Alara 2008-2009
        list["ARB"] = R.drawable.ic_arb
        list["CON"] = R.drawable.ic_con
        list["ALA"] = R.drawable.ic_ala

        //Lorwyn-Shadowmoor Block 2007-2008
        list["EVE"] = R.drawable.ic_eve
        list["SHM"] = R.drawable.ic_shm
        list["MOR"] = R.drawable.ic_mor
        list["LRW"] = R.drawable.ic_lrw

        //Time Spiral Block 2006-2007
        list["FUT"] = R.drawable.ic_fut
        list["PLC"] = R.drawable.ic_plc
        list["TSP"] = R.drawable.ic_tsp
        list["TSB"] = R.drawable.ic_tsp

        //Ice Age Block 2006
        list["CSP"] = R.drawable.ic_csp
        list["CST"] = R.drawable.ic_csp

        //Ravnica 2005-2006
        list["DIS"] = R.drawable.ic_dis
        list["GPT"] = R.drawable.ic_gpt
        list["RAV"] = R.drawable.ic_rav

        //Kamigawa Block 2004-2005
        list["SOK"] = R.drawable.ic_sok
        list["BOK"] = R.drawable.ic_bok
        list["CHK"] = R.drawable.ic_chk

        //Mirrodin Block 2003-2004
        list["5DN"] = R.drawable.ic_5dn
        list["DST"] = R.drawable.ic_dst
        list["MRD"] = R.drawable.ic_mrd

        //Onslaught Block 2002-2003
        list["SCG"] = R.drawable.ic_scg
        list["LGN"] = R.drawable.ic_lgn
        list["ONS"] = R.drawable.ic_ons

        //Odyssey Block 2001-2002
        list["JUD"] = R.drawable.ic_jud
        list["TOR"] = R.drawable.ic_tor
        list["ODY"] = R.drawable.ic_ody

        //Invasion Block 2000-2001
        list["APC"] = R.drawable.ic_apc
        list["PLS"] = R.drawable.ic_pls
        list["INV"] = R.drawable.ic_inv

        //Masques Block 1999-2000
        list["PCY"] = R.drawable.ic_pcy
        list["NMS"] = R.drawable.ic_nem
        list["MMQ"] = R.drawable.ic_mmq

        //Urza Block 1998-1999
        list["UDS"] = R.drawable.ic_uds
        list["ULG"] = R.drawable.ic_ulg
        list["USG"] = R.drawable.ic_usg

        //Tempest Block 1997-1998
        list["EXO"] = R.drawable.ic_exo
        list["STH"] = R.drawable.ic_sth
        list["TMP"] = R.drawable.ic_tmp

        //Mirage Block 1996-1997
        list["VIS"] = R.drawable.ic_vis
        list["WTH"] = R.drawable.ic_wth
        list["MIR"] = R.drawable.ic_mir

        //Ice Age Block 1995-1996
        list["ALL"] = R.drawable.ic_all
        list["ICE"] = R.drawable.ic_ice

        //Homelands Block 1995
        list["HML"] = R.drawable.ic_hml

        //Core sets
        list["M19"] = R.drawable.ic_m19
        list["M15"] = R.drawable.ic_m15
        list["M14"] = R.drawable.ic_m14
        list["M13"] = R.drawable.ic_m13
        list["M12"] = R.drawable.ic_m12
        list["M11"] = R.drawable.ic_m11
        list["M10"] = R.drawable.ic_m10
        list["10E"] = R.drawable.ic_10e
        list["9ED"] = R.drawable.ic_9ed
        list["8ED"] = R.drawable.ic_8ed
        list["7ED"] = R.drawable.ic_7ed
        list["6ED"] = R.drawable.ic_6ed
        list["5ED"] = R.drawable.ic_5ed

        //Signature Spellbook
        list["SS1"] = R.drawable.ic_ss1

        //Global Series
        list["GS1"] = R.drawable.ic_gs1

        //Early sets
        list["DKM"] = R.drawable.ic_dkm
        list["BTD"] = R.drawable.ic_btd
        list["BRB"] = R.drawable.ic_brb
        list["S00"] = R.drawable.ic_s00
        list["PTK"] = R.drawable.ic_ptk
        list["ATH"] = R.drawable.ic_ath
        list["PO2"] = R.drawable.ic_p02
        list["POR"] = R.drawable.ic_por
        list["pPOD"] = R.drawable.ic_por
        list["VAN"] = R.drawable.ic_dpa
        list["CHR"] = R.drawable.ic_chr
        list["4ED"] = R.drawable.ic_4ed
        list["FEM"] = R.drawable.ic_fem
        list["DRK"] = R.drawable.ic_drk
        list["LEG"] = R.drawable.ic_leg
        list["ATQ"] = R.drawable.ic_atq
        list["ARN"] = R.drawable.ic_arn
        list["CEI"] = R.drawable.ic_cei
        list["CED"] = R.drawable.ic_ced
        list["SUM"] = R.drawable.ic_3ed
        list["3ED"] = R.drawable.ic_3ed
        list["2ED"] = R.drawable.ic_2ed
        list["LEB"] = R.drawable.ic_leb
        list["LEA"] = R.drawable.ic_lea

        //Conspiracy
        list["CN2"] = R.drawable.ic_cn2
        list["CNS"] = R.drawable.ic_cns

        //Welcome deck
        list["W17"] = R.drawable.ic_w17
        list["W16"] = R.drawable.ic_w16
        list["S99"] = R.drawable.ic_s99

        //Commander
        list["CMA"] = R.drawable.ic_cma
        list["C18"] = R.drawable.ic_c18
        list["C17"] = R.drawable.ic_c17
        list["C16"] = R.drawable.ic_c16
        list["C15"] = R.drawable.ic_c15
        list["C14"] = R.drawable.ic_c14
        list["C13"] = R.drawable.ic_c13
        list["CMD"] = R.drawable.ic_cmd
        list["CM1"] = R.drawable.ic_cm1
        list["CM2"] = R.drawable.ic_cm2

        //Duel deck
        list["DDU"] = R.drawable.ic_ddu
        list["DDT"] = R.drawable.ic_ddt
        list["DDS"] = R.drawable.ic_dds
        list["DDR"] = R.drawable.ic_ddr
        list["DDQ"] = R.drawable.ic_ddq
        list["DDP"] = R.drawable.ic_ddp
        list["DDO"] = R.drawable.ic_ddo
        list["DDN"] = R.drawable.ic_ddn
        list["DDM"] = R.drawable.ic_ddm
        list["DDL"] = R.drawable.ic_ddl
        list["DDK"] = R.drawable.ic_ddk
        list["DDH"] = R.drawable.ic_ddh
        list["DDJ"] = R.drawable.ic_ddj
        list["DDI"] = R.drawable.ic_ddi
        list["DDG"] = R.drawable.ic_ddg
        list["DDF"] = R.drawable.ic_ddf
        list["DDE"] = R.drawable.ic_dde
        list["DDD"] = R.drawable.ic_ddd
        list["DDC"] = R.drawable.ic_ddc
        list["DD2"] = R.drawable.ic_dd2
        list["EVG"] = R.drawable.ic_dd1
        list["DD3_JVC"] = R.drawable.ic_dd2
        list["DD3_DVD"] = R.drawable.ic_ddc
        list["DD3_GVL"] = R.drawable.ic_ddd
        list["DD3_EVG"] = R.drawable.ic_dd1

        //Casual
        list["GNT"] = R.drawable.ic_gnt
        list["E02"] = R.drawable.ic_e02
        list["E01"] = R.drawable.ic_e01
        list["ARC"] = R.drawable.ic_arc
        list["PCA"] = R.drawable.ic_pca
        list["PC2"] = R.drawable.ic_pc2
        list["HOP"] = R.drawable.ic_hop
        list["DPA"] = R.drawable.ic_dpa

        //Un-sets
        list["UST"] = R.drawable.ic_ust
        list["UNH"] = R.drawable.ic_unh
        list["UGL"] = R.drawable.ic_ugl

        //Masters
        list["UMA"] = R.drawable.ic_uma
        list["A25"] = R.drawable.ic_a25
        list["IMA"] = R.drawable.ic_ima
        list["EMA"] = R.drawable.ic_ema
        list["MM3"] = R.drawable.ic_mm3
        list["MM2"] = R.drawable.ic_mm2
        list["MMA"] = R.drawable.ic_mma

        //Magic Online
        list["VMA"] = R.drawable.ic_vma
        list["TPR"] = R.drawable.ic_tpr
        list["ME4"] = R.drawable.ic_me4
        list["ME3"] = R.drawable.ic_me3
        list["ME2"] = R.drawable.ic_me2
        list["MED"] = R.drawable.ic_med

        //From the vault
        list["V17"] = R.drawable.ic_v17
        list["V16"] = R.drawable.ic_v16
        list["V15"] = R.drawable.ic_v15
        list["V14"] = R.drawable.ic_v14
        list["V13"] = R.drawable.ic_v13
        list["V12"] = R.drawable.ic_v12
        list["V11"] = R.drawable.ic_v11
        list["V10"] = R.drawable.ic_v10
        list["V09"] = R.drawable.ic_v09
        list["DRB"] = R.drawable.ic_drb

        //Ready-to-Play Decks
        list["PD3"] = R.drawable.ic_pd3
        list["PD2"] = R.drawable.ic_pd2
        list["H09"] = R.drawable.ic_h09

        //Promo
        list["MD1"] = R.drawable.ic_md1
        list["CP3"] = R.drawable.ic_promo
        list["CP2"] = R.drawable.ic_promo
        list["CP1"] = R.drawable.ic_promo
        list["pWCQ"] = R.drawable.ic_promo
        list["pMGD"] = R.drawable.ic_promo
        list["pGPX"] = R.drawable.ic_promo
        list["pWPN"] = R.drawable.ic_promo
        list["pLPA"] = R.drawable.ic_promo
        list["pSUM"] = R.drawable.ic_promo
        list["pPRO"] = R.drawable.ic_promo
        list["pHHO"] = R.drawable.ic_promo
        list["pCMP"] = R.drawable.ic_promo
        list["pGTW"] = R.drawable.ic_promo
        list["p2HG"] = R.drawable.ic_promo
        list["pPRE"] = R.drawable.ic_promo
        list["pMEI"] = R.drawable.ic_promo
        list["pFNM"] = R.drawable.ic_promo
        list["pJGP"] = R.drawable.ic_promo
        list["pLGM"] = R.drawable.ic_promo
        list["pCEL"] = R.drawable.ic_promo
        list["pGRU"] = R.drawable.ic_pgru
        list["pWOR"] = R.drawable.ic_promo
        list["pWOS"] = R.drawable.ic_promo
        list["pSUS"] = R.drawable.ic_promo
        list["pELP"] = R.drawable.ic_promo
        list["pMPR"] = R.drawable.ic_promo
        list["pDRC"] = R.drawable.ic_pdrc
        list["p15A"] = R.drawable.ic_dpa
        list["pALP"] = R.drawable.ic_dpa
        list["RQS"] = R.drawable.ic_dpa
        list["pREL"] = R.drawable.ic_dpa
        list["pARL"] = R.drawable.ic_dpa
        list["MGB"] = R.drawable.ic_dpa
        list["ITP"] = R.drawable.ic_dpa

    }

    fun getIcon(set: String): Int {
        return list[set] ?: R.drawable.ic_circle
    }

    fun getColor(rare: String): Int {
        return colors[rare] ?: R.color.rarityCommon
    }

}
